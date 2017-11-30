/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 17, 2012
 */
package com.graphscape.gwt.core.gwthandlers;

import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.graphscape.gwt.core.support.GwtHandlerSupport;

/**
 * @author wu
 * 
 */
public abstract class GwtMouseDownHandler extends
		GwtHandlerSupport<MouseDownEvent, MouseDownHandler> implements
		MouseDownHandler {
	@Override
	public void onMouseDown(MouseDownEvent evt) {
		super.handle(evt);
	}
}
