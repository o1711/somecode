/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 17, 2012
 */
package com.graphscape.gwt.core.gwthandlers;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.graphscape.gwt.core.support.GwtHandlerSupport;

/**
 * @author wu
 * 
 */
public abstract class GwtChangeHandler extends
		GwtHandlerSupport<ChangeEvent, ChangeHandler> implements ChangeHandler {

	/*
	 * Nov 17, 2012
	 */
	@Override
	public void onChange(ChangeEvent changeevent) {
		//
		this.handle(changeevent);
	}

}
