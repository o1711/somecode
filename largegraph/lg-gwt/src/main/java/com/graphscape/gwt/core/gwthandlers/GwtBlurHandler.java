/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 17, 2012
 */
package com.graphscape.gwt.core.gwthandlers;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.graphscape.gwt.core.support.GwtHandlerSupport;

/**
 * @author wu
 * 
 */
public abstract class GwtBlurHandler extends
		GwtHandlerSupport<BlurEvent, BlurHandler> implements BlurHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.event.dom.client.MouseUpHandler#onMouseUp(com.google.gwt
	 * .event.dom.client.MouseUpEvent)
	 */
	@Override
	public void onBlur(BlurEvent event) {
		this.handle(event);// o
	}

}
