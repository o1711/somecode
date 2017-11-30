/**
 *  Dec 21, 2012
 */
package com.graphscape.gwt.core.event;

import com.graphscape.gwt.core.endpoint.EndPointI;


/**
 * @author wuzhen
 * 
 */
public class EndpointOpenEvent extends EndpointEvent {

	public static final Type<EndpointOpenEvent> TYPE = new Type<EndpointOpenEvent>(
			EndpointEvent.TYPE, "open");

	/**
	 * @param type
	 */
	public EndpointOpenEvent(EndPointI c) {
		super(TYPE, c);
	}

}
