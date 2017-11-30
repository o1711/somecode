/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 26, 2012
 */
package com.graphscape.gwt.whosegraph.event;

import com.graphscape.gwt.core.data.message.MessageData;
import com.graphscape.gwt.core.endpoint.EndPointI;

/**
 * @author wu
 * 
 */
public class SuccessMessageEvent extends MessageEvent {

	public static final Type<SuccessMessageEvent> TYPE = new Type<SuccessMessageEvent>(MessageEvent.TYPE, "success");

	/**
	 * @param type
	 */
	public SuccessMessageEvent(EndPointI src, MessageData md) {
		super(TYPE, src, md);
	}

}
