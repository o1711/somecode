/**
 * All right is from Author of the file.
 * Any usage of the code must be authorized by the the auther.
 * If not sure the detail of the license,please distroy the copies immediately.  
 * Nov 20, 2012
 */
package com.graphscape.gwt.core.event;

import com.graphscape.gwt.core.core.Event;

/**
 * @author wuzhen
 * @deprecated TODO 
 */
public class AfterEventHandleEvent extends Event {

	public static final Type<AfterEventHandleEvent> TYPE = new Type<AfterEventHandleEvent>("eventhandle");

	private Event event;

	/** */
	public AfterEventHandleEvent(Event event) {
		super(TYPE, event.getSource());
		this.event = event;
	}

	public Event getEvent() {
		return event;
	}

}
