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
public class EndpointBusyEvent extends EndpointEvent {

	public static final Event.Type<EndpointBusyEvent> TYPE = new Event.Type<EndpointBusyEvent>(
			EndpointEvent.TYPE, "busy");

	/**
	 * @param type
	 */
	public EndpointBusyEvent(EndPointI src) {
		super(TYPE, src);
	}

}
