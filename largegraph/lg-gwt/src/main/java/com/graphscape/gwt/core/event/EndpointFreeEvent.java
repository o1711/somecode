/**
 * All right is from Author of the file,to be explained in comming days.
 * Apr 4, 2013
 */
package com.graphscape.gwt.core.event;

import com.graphscape.gwt.core.core.Event;
import com.graphscape.gwt.core.endpoint.EndPointI;

/**
 * @author wu
 * 
 */
public class EndpointFreeEvent extends EndpointEvent {

	public static final Event.Type<EndpointFreeEvent> TYPE = new Event.Type<EndpointFreeEvent>(
			EndpointEvent.TYPE, "free");

	/**
	 * @param type
	 */
	public EndpointFreeEvent(EndPointI src) {
		super(TYPE, src);
	}

}
