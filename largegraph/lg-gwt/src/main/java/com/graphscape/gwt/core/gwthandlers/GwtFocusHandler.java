/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 17, 2012
 */
package com.graphscape.gwt.core.gwthandlers;

import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.graphscape.gwt.core.support.GwtHandlerSupport;

/**
 * @author wu
 * 
 */
public abstract class GwtFocusHandler extends GwtHandlerSupport<FocusEvent, FocusHandler> implements
		FocusHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.event.dom.client.MouseUpHandler#onMouseUp(com.google.gwt
	 * .event.dom.client.MouseUpEvent)
	 */
	@Override
	public void onFocus(FocusEvent event) {
		this.handle(event);// o
	}

}
