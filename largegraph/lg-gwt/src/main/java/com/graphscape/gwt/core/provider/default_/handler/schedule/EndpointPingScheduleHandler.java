/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 11, 2013
 */
package com.graphscape.gwt.core.provider.default_.handler.schedule;

import com.graphscape.gwt.core.MsgWrapper;
import com.graphscape.gwt.core.core.Event.EventHandlerI;
import com.graphscape.gwt.core.endpoint.EndPointI;
import com.graphscape.gwt.core.event.ScheduleEvent;

/**
 * @author wu
 * 
 */
public class EndpointPingScheduleHandler implements EventHandlerI<ScheduleEvent> {

	private EndPointI endpoint;

	public EndpointPingScheduleHandler(EndPointI ep) {
		this.endpoint = ep;
	}

	/*
	 * Jan 11, 2013
	 */
	@Override
	public void handle(ScheduleEvent t) {
		
	}

}
