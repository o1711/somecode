/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 26, 2012
 */
package com.graphscape.gwt.whosegraph.event;

import com.graphscape.gwt.core.core.Event;
import com.graphscape.gwt.core.data.message.MessageData;
import com.graphscape.gwt.core.endpoint.EndPointI;

/**
 * @author wu
 * 
 */
public class MessageEvent extends Event {

	public static final Type<MessageEvent> TYPE = new Type<MessageEvent>("message");

	private MessageData message;

	/**
	 * @param type
	 */
	public MessageEvent(Type<? extends MessageEvent> type, EndPointI src, MessageData md) {
		super(type, src);
		this.message = md;
	}

	/**
	 * @return the message
	 */
	public MessageData getMessage() {
		return message;
	}

}
