/**
 * All right is from Author of the file,to be explained in comming days.
 * Sep 30, 2012
 */
package com.graphscape.gwt.core.event;

import com.graphscape.gwt.core.core.Event;
import com.graphscape.gwt.core.core.UiObjectI;

/**
 * @author wu
 * 
 */
public class LocalEvent extends Event {

	public static final Event.Type<LocalEvent> TYPE = new Event.Type<LocalEvent>("local");

	/**
	 * @param type
	 */
	public LocalEvent(Type<? extends Event> type, UiObjectI src) {
		super(type, src);
		this.isGlobal = false;
	}

}
