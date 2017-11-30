/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 17, 2012
 */
package com.graphscape.gwt.core.gwthandlers;

import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.graphscape.gwt.core.support.GwtHandlerSupport;

/**
 * @author wu
 * 
 */
public abstract class GwtMouseOverHandler extends GwtHandlerSupport<MouseOverEvent, MouseOverHandler>
		implements MouseOverHandler {
	@Override
	public void onMouseOver(MouseOverEvent evt) {
		super.handle(evt);
	}
}
