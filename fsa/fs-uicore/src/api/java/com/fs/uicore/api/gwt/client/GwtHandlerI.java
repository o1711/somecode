/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 17, 2012
 */
package com.fs.uicore.api.gwt.client;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author wu
 * 
 */
public interface GwtHandlerI<E extends GwtEvent<H>, H extends EventHandler> {

	public H cast();

}
