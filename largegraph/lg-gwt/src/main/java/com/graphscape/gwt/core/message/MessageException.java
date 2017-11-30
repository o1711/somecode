/**
 *  Dec 24, 2012
 */
package com.graphscape.gwt.core.message;

import com.graphscape.gwt.core.MsgWrapper;

/**
 * @author wuzhen
 * 
 */
public class MessageException {

	protected Throwable exception;
	protected MsgWrapper messageData;

	public Throwable getException() {
		return exception;
	}

	public MsgWrapper getMessageData() {
		return messageData;
	}

	public MessageException(Throwable t, MsgWrapper md) {
		this.exception = t;
		this.messageData = md;
	}
}
