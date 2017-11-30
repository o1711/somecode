/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 17, 2012
 */
package com.graphscape.gwt.core.gwthandlers;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.graphscape.gwt.core.support.GwtHandlerSupport;

/**
 * @author wu
 * 
 */
public abstract class GwtMouseMoveHandler extends
		GwtHandlerSupport<MouseMoveEvent, MouseMoveHandler> implements
		MouseMoveHandler {
	@Override
	public void onMouseMove(MouseMoveEvent evt) {
		super.handle(evt);
	}
}
