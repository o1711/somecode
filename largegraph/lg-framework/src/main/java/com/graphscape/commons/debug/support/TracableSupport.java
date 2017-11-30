package com.graphscape.commons.debug.support;

import java.util.ArrayList;
import java.util.List;

import com.graphscape.commons.debug.TracableI;
import com.graphscape.commons.debug.TracerI;

public class TracableSupport extends ProfileAwareSupport implements TracableI {

	protected TracerI tracer;

	protected List<Object> childToBeApplied = new ArrayList<Object>();

	@Override
	public TracerI getTracer() {

		return this.tracer;
	}

	@Override
	public void setTracer(TracerI tracer) {
		this.tracer = tracer;
		if (this.tracer == null) {
			return;
		}

		this.tracer.applyAll(this.childToBeApplied);
		this.childToBeApplied.clear();

	}
	
	protected void onException(Throwable e){
		if(this.tracer == null){
			return;
		}
		this.tracer.onException(e);
		
	}
	
	protected void beforeExecute(Object... msg) {
		if (this.tracer == null) {
			return;
		}
		this.tracer.beforeExecute(this, msg);

	}

	protected void afterExecute() {
		if (this.tracer == null) {
			return;
		}
		this.tracer.afterExecute();
	}

	protected <T> T applyTracer(T obj) {
		if (this.tracer == null) {
			this.childToBeApplied.add(obj);
			return obj;
		}

		this.tracer.apply(obj);
		return obj;

	}

	@Override
	public String getName() {
		return this.getClass().getName();
	}
}
