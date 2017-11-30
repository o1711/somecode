/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 17, 2012
 */
package com.graphscape.gwt.core.gwthandlers;

import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.graphscape.gwt.core.support.GwtHandlerSupport;

/**
 * @author wu
 * 
 */
public abstract class GwtMouseUpHandler extends
		GwtHandlerSupport<MouseUpEvent, MouseUpHandler> implements
		MouseUpHandler {
	@Override
	public void onMouseUp(MouseUpEvent evt) {
		super.handle(evt);
	}
}
