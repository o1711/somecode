/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 2, 2013
 */
package com.graphscape.gwt.commons.provider.default_.handler.message;

import com.google.gwt.user.client.Window;
import com.graphscape.gwt.commons.mvc.support.UiHandlerSupport;
import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.data.message.MessageData;
import com.graphscape.gwt.core.event.EndpointMessageEvent;
import com.graphscape.gwt.core.message.MessageHandlerI;

/**
 * @author wu
 * 
 */
public class PasswordForgotSuccessMH extends UiHandlerSupport implements MessageHandlerI<EndpointMessageEvent> {

	/**
	 * @param c
	 */
	public PasswordForgotSuccessMH(ContainerI c) {
		super(c);
	}

	/*
	 * Jan 2, 2013
	 */
	@Override
	public void handle(EndpointMessageEvent t) {

		//
		MessageData res = t.getMessage();
		// notify user a message send to the email
		Window.alert("A email has sent to the you,please check it and following the instruction there!");
	}

}
