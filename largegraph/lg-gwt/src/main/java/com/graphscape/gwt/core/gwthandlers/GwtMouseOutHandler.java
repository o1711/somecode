/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 17, 2012
 */
package com.graphscape.gwt.core.gwthandlers;

import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.graphscape.gwt.core.support.GwtHandlerSupport;

/**
 * @author wu
 * 
 */
public abstract class GwtMouseOutHandler extends GwtHandlerSupport<MouseOutEvent, MouseOutHandler> implements
		MouseOutHandler {
	@Override
	public void onMouseOut(MouseOutEvent evt) {
		super.handle(evt);
	}
}
