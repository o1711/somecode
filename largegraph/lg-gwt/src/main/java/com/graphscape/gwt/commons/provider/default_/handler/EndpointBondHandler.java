/**
 *  Jan 31, 2013
 */
package com.graphscape.gwt.commons.provider.default_.handler;

import com.graphscape.gwt.commons.event.RegisterUserLoginEvent;
import com.graphscape.gwt.commons.event.UserLoginEvent;
import com.graphscape.gwt.commons.mvc.support.UiHandlerSupport;
import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.core.Event.EventHandlerI;
import com.graphscape.gwt.core.endpoint.UserInfo;
import com.graphscape.gwt.core.event.EndpointBondEvent;

/**
 * @author wuzhen
 * 
 */
public class EndpointBondHandler extends UiHandlerSupport implements EventHandlerI<EndpointBondEvent> {

	/**
	 * @param c
	 */
	public EndpointBondHandler(ContainerI c) {
		super(c);
	}

	@Override
	public void handle(EndpointBondEvent e) {
		//
		UserInfo ui = e.getChannel().getUserInfo();
		if (ui.isAnonymous()) {
			new UserLoginEvent(e.getSource(), ui).dispatch();
		} else {
			new RegisterUserLoginEvent(e.getSource(), ui).dispatch();
		}
	}
}
