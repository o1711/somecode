/**
 * All right is from Author of the file,to be explained in comming days.
 * Sep 22, 2012
 */
package com.graphscape.gwt.core.provider.default_;

import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.EventBusI;
import com.graphscape.gwt.core.support.UiObjectSupport;

/**
 * @author wu
 * 
 */
public class EventBusImpl extends UiObjectSupport implements EventBusI {

	/**
	 * @param c
	 */
	public EventBusImpl(ContainerI c) {
		super(c);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.commons.uicore.api.gwt.client.support.UiObjectSupport#getEventBus()
	 */
	@Override
	public EventBusI getEventBus(boolean force) {
		return this;
	}


}
