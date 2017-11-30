/**
 *  Jan 31, 2013
 */
package com.graphscape.gwt.commons.provider.default_.handler;

import com.graphscape.gwt.commons.event.HeaderItemEvent;
import com.graphscape.gwt.commons.frwk.login.LoginControlI;
import com.graphscape.gwt.commons.mvc.support.UiHandlerSupport;
import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.core.Event.EventHandlerI;

/**
 * @author wuzhen
 * 
 */
public class LoginHeaderItemHandler extends UiHandlerSupport implements EventHandlerI<HeaderItemEvent> {

	/**
	 * @param c
	 */
	public LoginHeaderItemHandler(ContainerI c) {
		super(c);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(HeaderItemEvent t) {
		LoginControlI lc = this.getControl(LoginControlI.class, true);
		lc.openLoginView(true);
	}

}
