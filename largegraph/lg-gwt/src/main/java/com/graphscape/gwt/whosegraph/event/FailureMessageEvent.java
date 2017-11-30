/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 26, 2012
 */
package com.graphscape.gwt.whosegraph.event;

import com.graphscape.gwt.core.core.Event.Type;
import com.graphscape.gwt.core.data.message.MessageData;
import com.graphscape.gwt.core.endpoint.EndPointI;

/**
 * @author wu
 * 
 */
public class FailureMessageEvent extends MessageEvent {

	public static final Type<FailureMessageEvent> TYPE = new Type<FailureMessageEvent>(MessageEvent.TYPE, "failure");

	/**
	 * @param type
	 */
	public FailureMessageEvent(EndPointI src, MessageData md) {
		super(TYPE, src, md);
	}

}
