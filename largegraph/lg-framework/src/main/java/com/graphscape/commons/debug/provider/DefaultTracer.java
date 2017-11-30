package com.graphscape.commons.debug.provider;

import java.util.List;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.graphscape.commons.debug.TracableI;
import com.graphscape.commons.debug.TracerI;
import com.graphscape.commons.handling.support.CollectionListener;
import com.graphscape.commons.lang.HandlerI;
import com.graphscape.commons.util.StringUtil;

public class DefaultTracer implements TracerI {

	private static class Entry {

		Object target;

		Object[] arguments;

		public Entry(Object obj, Object[] arguments) {
			this.target = obj;
			this.arguments = arguments;
		}

	}

	private static final Logger LOG = LoggerFactory.getLogger(DefaultTracer.class);

	protected Stack<Entry> stack = new Stack<Entry>();

	protected boolean logStackContent = true;

	protected CollectionListener<TracerI> afterPushListener = new CollectionListener<TracerI>();

	protected long index = -1;

	protected boolean disable;

	protected boolean enableLogPop;

	@Override
	public TracerI disable() {
		this.disable = true;
		return this;
	}

	@Override
	public void beforeExecute(Object obj, Object... args) {
		if (this.disable) {
			return;
		}
		this.index++;
		Entry en=new Entry(obj, args);
		
		this.stack.push(en);

		this.afterPushListener.handle(this);//
		this.log(true, en);

	}

	protected void log(boolean push, Entry en) {
		int size = this.stack.size();
		if (!push) {
			size++;
		}
		Object obj = en.target;
		Object[] args = en.arguments;
		StringBuffer msg = new StringBuffer();
		msg.append("[TRACE:" + StringUtil.fillSpace(4, "" + this.index) + "]");
		msg.append("[DEPTH:" + StringUtil.fillSpace(4, "" + size) + "]");

		msg.append(StringUtil.space(size));
		msg.append("(" + size + ")");
		msg.append(push ? ">>" : "<<");

		msg.append("[" + (obj == null ? "" : obj) + "]");

		for (int i = 0; i < args.length; i++) {

			msg.append(args[i]);
			msg.append(",");
		}
		LOG.trace(msg.toString());
	}

	@Override
	public void afterExecute() {
		if (this.disable) {
			return;//
		}
		Entry rt = this.stack.pop();
		if (this.stack.isEmpty()) {
			// LOG.trace("EMPTY STACK");
		}
		if (this.enableLogPop) {
			this.log(false, rt);
		}
		return;
	}

	@Override
	public boolean apply(Object obj) {
		if (obj instanceof TracableI) {
			((TracableI) obj).setTracer(this);
			return true;
		}

		return false;
	}

	@Override
	public <T> int applyAll(List<T> list) {
		int rt = 0;
		for (Object obj : list) {
			if (this.apply(obj)) {
				rt++;
			}
		}
		return rt;
	}

	@Override
	public void addAfterPushListener(HandlerI<TracerI> l) {
		this.afterPushListener.addHandler(l);
	}

	@Override
	public int getDepth() {
		return this.stack.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.commons.debug.TracerI#onException(java.lang.Throwable)
	 */
	@Override
	public void onException(Throwable e) {
		LOG.error("", e);
	}

}
