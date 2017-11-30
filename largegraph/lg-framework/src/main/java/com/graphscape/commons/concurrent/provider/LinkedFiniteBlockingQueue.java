package com.graphscape.commons.concurrent.provider;

import java.util.NoSuchElementException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.graphscape.commons.concurrent.FiniteBlockingQueueI;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.lang.Wrapper;
import com.graphscape.commons.util.TimeAndUnit;

public class LinkedFiniteBlockingQueue<T> implements FiniteBlockingQueueI<T> {

	TimeAndUnit INFINITE = TimeAndUnit.valueOf(Long.MAX_VALUE, TimeUnit.DAYS);

	private static class Element<T> extends Wrapper<T> {

		private boolean isClose;

		public Element(T t, boolean isFinish) {
			super(t);
			this.isClose = isFinish;
		}

	}

	private BlockingQueue<Element<T>> queue;

	private boolean closing;

	private boolean closed;

	public LinkedFiniteBlockingQueue() {
		super();
		this.queue = new LinkedBlockingQueue<Element<T>>();
	}

	@Override
	public void close() {
		try {
			this.closing = true;
			this.queue.put(new Element<T>(null, true));
		} catch (InterruptedException e) {
			throw new GsException(e);
		}
	}

	@Override
	public T poll(TimeAndUnit timeout) {
		this.assertNotClosed();
		Element<T> e;
		try {
			e = this.queue.poll(timeout.getValue(), timeout.getUnit());
		} catch (InterruptedException e1) {
			throw new GsException(e1);
		}
		if (e == null) {
			return null;
		}
		if (e.isClose) {
			this.closed = true;
			throw new NoSuchElementException();
		}
		T rt = e.getTarget();
		return rt;
	}

	public void assertNotClosed() {
		if (this.closed) {
			throw new NoSuchElementException();
		}
	}

	@Override
	public void put(T t) {
		if (this.closed) {
			throw new GsException("closed");
		}

		if (this.closing) {
			throw new GsException("closing");
		}
		try {
			this.queue.put(new Element<T>(t, false));
		} catch (InterruptedException e) {
			throw new GsException(e);
		}

	}

	@Override
	public T take() {
		while (true) {
			T rt = this.poll(INFINITE);
			if (rt != null) {
				return rt;
			}//
			//else try next loop.
		}
	}

	@Override
	public boolean isClosed() {

		return this.closed;
	}

}
