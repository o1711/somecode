/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 17, 2012
 */
package com.graphscape.gwt.core.gwthandlers;

import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.graphscape.gwt.core.support.GwtHandlerSupport;

/**
 * @author wu
 * 
 */
public abstract class GwtKeyUpHandler extends
		GwtHandlerSupport<KeyUpEvent, KeyUpHandler> implements KeyUpHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.event.dom.client.MouseUpHandler#onMouseUp(com.google.gwt
	 * .event.dom.client.MouseUpEvent)
	 */
	@Override
	public void onKeyUp(KeyUpEvent event) {
		this.handle(event);// o
	}

}
