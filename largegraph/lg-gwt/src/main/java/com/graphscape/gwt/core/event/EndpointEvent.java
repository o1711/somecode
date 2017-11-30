/**
 *  Dec 21, 2012
 */
package com.graphscape.gwt.core.event;

import com.graphscape.gwt.core.core.Event;
import com.graphscape.gwt.core.data.message.MessageData;
import com.graphscape.gwt.core.endpoint.EndPointI;

/**
 * @author wuzhen
 * 
 */
public class EndpointEvent extends Event {

	public static final Type<EndpointEvent> TYPE = new Type<EndpointEvent>("endpoint");

	/**
	 * @param type
	 */
	public EndpointEvent(Type<? extends EndpointEvent> type, EndPointI source) {
		super(type, source);

	}

	protected EndpointEvent(Type<? extends EndpointEvent> type, EndPointI source, MessageData md) {
		super(type, source, md);
	}

	public EndPointI getEndPoint() {
		return (EndPointI) this.source;
	}

	public EndPointI getChannel() {
		return (EndPointI) this.source;
	}

}
