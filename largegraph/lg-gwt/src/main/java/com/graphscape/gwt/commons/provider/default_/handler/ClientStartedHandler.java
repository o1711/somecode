/**
 *  Jan 31, 2013
 */
package com.graphscape.gwt.commons.provider.default_.handler;

import java.util.List;
import java.util.Map;

import com.graphscape.gwt.commons.HeaderItems;
import com.graphscape.gwt.commons.event.AutoLoginRequireEvent;
import com.graphscape.gwt.commons.frwk.FrwkControlI;
import com.graphscape.gwt.commons.frwk.login.LoginControlI;
import com.graphscape.gwt.commons.frwk.password.PasswordResetViewI;
import com.graphscape.gwt.commons.mvc.support.UiHandlerSupport;
import com.graphscape.gwt.commons.provider.default_.EndpointKeeper;
import com.graphscape.gwt.commons.provider.default_.handler.message.LoginFailureMH;
import com.graphscape.gwt.commons.provider.default_.handler.message.LoginSuccessMH;
import com.graphscape.gwt.commons.provider.default_.handler.message.PasswordForgotFailureMH;
import com.graphscape.gwt.commons.provider.default_.handler.message.PasswordForgotSuccessMH;
import com.graphscape.gwt.commons.provider.default_.handler.message.PasswordResetFailureMH;
import com.graphscape.gwt.commons.provider.default_.handler.message.PasswordResetSuccessMH;
import com.graphscape.gwt.commons.provider.default_.handler.message.SignupAnonymousSuccessMH;
import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.ClientI;
import com.graphscape.gwt.core.UiException;
import com.graphscape.gwt.core.commons.Path;
import com.graphscape.gwt.core.core.Event.EventHandlerI;
import com.graphscape.gwt.core.endpoint.EndPointI;
import com.graphscape.gwt.core.event.AfterClientStartEvent;
import com.graphscape.gwt.core.window.UiWindow;

/**
 * @author wuzhen
 * 
 */
public class ClientStartedHandler extends UiHandlerSupport implements EventHandlerI<AfterClientStartEvent> {

	/**
	 * @param c
	 */
	public ClientStartedHandler(ContainerI c) {
		super(c);
	}

	@Override
	public void handle(AfterClientStartEvent e) {
		
		this.activeMessageHandlers(this.container, e.getClient());
		
		//
		//heatbeat
		EndpointKeeper ek = new EndpointKeeper(this.getClient(true));
		ek.start();//
		
		// start frwk view.
		FrwkControlI fc = this.getControl(FrwkControlI.class, true);
		fc.open();
		fc.addHeaderItem(HeaderItems.USER_LOGIN);
		//
		String action = UiWindow.getParameter("action", null);
		if (action == null) {
			// TODO remove this event
			new AutoLoginRequireEvent(e.getSource()).dispatch();
		} else {
			Map<String, List<String>> pm = com.google.gwt.user.client.Window.Location.getParameterMap();
			if (action.equals("pf")) {
				String pfId = UiWindow.getParameter("pfId", null);
				if (pfId == null) {
					throw new UiException("pfId is null");
				}
				LoginControlI lc = this.getControl(LoginControlI.class, true);
				PasswordResetViewI pv = lc.openPasswordResetView();
				pv.setPfId(pfId);

			} else {
				throw new UiException("no this action:" + action);
			}
		}
	}
	
	public void activeMessageHandlers(ContainerI c, ClientI client) {
		EndPointI ep = client.getEndpoint(true);
		ep.addHandler(Path.valueOf("/endpoint/message/signup/anonymous/success"),
				new SignupAnonymousSuccessMH(c));
		ep.addHandler(Path.valueOf("/endpoint/message/terminal/auth/success"), new LoginSuccessMH(c));
		ep.addHandler(Path.valueOf("/endpoint/message/terminal/auth/failure"), new LoginFailureMH(c));
		ep.addHandler(Path.valueOf("/endpoint/message/password/forgot/success"), new PasswordForgotSuccessMH(
				c));
		ep.addHandler(Path.valueOf("/endpoint/message/password/forgot/failure"), new PasswordForgotFailureMH(
				c));
		ep.addHandler(Path.valueOf("/endpoint/message/password/reset/failure"), new PasswordResetFailureMH(c));
		ep.addHandler(Path.valueOf("/endpoint/message/password/reset/success"), new PasswordResetSuccessMH(c));

	}

}
