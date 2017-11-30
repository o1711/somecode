/**
 *  Dec 21, 2012
 */
package com.graphscape.gwt.core.event;

import com.graphscape.gwt.core.data.message.MessageData;
import com.graphscape.gwt.core.endpoint.EndPointI;

/**
 * @author wuzhen
 * 
 */
public class EndpointMessageEvent extends EndpointEvent {

	public static final Type<EndpointMessageEvent> TYPE = new Type<EndpointMessageEvent>(EndpointEvent.TYPE,
			"message");

	/**
	 * @param type
	 */
	public EndpointMessageEvent(EndPointI c, MessageData md) {
		super(TYPE, c, md);
	}

}
