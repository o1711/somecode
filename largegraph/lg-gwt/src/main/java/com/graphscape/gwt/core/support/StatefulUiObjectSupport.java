/**
 * All right is from Author of the file,to be explained in comming days.
 * Sep 23, 2012
 */
package com.graphscape.gwt.core.support;

import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.event.StateChangeEvent;
import com.graphscape.gwt.core.state.State;
import com.graphscape.gwt.core.state.StatefulI;

/**
 * @author wu
 * 
 */
public class StatefulUiObjectSupport extends UiObjectSupport implements StatefulI {

	/**
	 * @param name
	 */
	public StatefulUiObjectSupport(ContainerI c) {
		this(c, null);
	}

	public StatefulUiObjectSupport(ContainerI c, String name) {
		super(c, name);

	}

	protected State state;

	@Override
	public State getState() {
		return this.state;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.commons.uicore.api.gwt.client.state.StatefulI#isState(com.fs.commons.uicore.api
	 * .gwt.client.state.State)
	 */
	@Override
	public boolean isState(State s) {
		return s.equals(this.state);
	}

	protected void setState(State news) {
		State old = this.state;
		this.state = news;
		
		new StateChangeEvent(this).dispatch();
	}

}
