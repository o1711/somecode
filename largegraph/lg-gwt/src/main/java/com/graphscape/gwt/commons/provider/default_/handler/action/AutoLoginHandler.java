package com.graphscape.gwt.commons.provider.default_.handler.action;

import com.graphscape.gwt.commons.event.AutoLoginRequireEvent;
import com.graphscape.gwt.commons.frwk.login.LoginControlI;
import com.graphscape.gwt.commons.mvc.support.UiHandlerSupport;
import com.graphscape.gwt.commons.provider.default_.frwk.login.AccountsLDW;
import com.graphscape.gwt.commons.provider.default_.frwk.login.AnonymousAccountLDW;
import com.graphscape.gwt.commons.provider.default_.frwk.login.RegisteredAccountLDW;
import com.graphscape.gwt.commons.provider.default_.handler.action.AutoLoginHandler;
import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.MsgWrapper;
import com.graphscape.gwt.core.commons.Path;
import com.graphscape.gwt.core.core.UiObjectI;
import com.graphscape.gwt.core.core.Event.EventHandlerI;
import com.graphscape.gwt.core.data.property.ObjectPropertiesData;
import com.graphscape.gwt.core.endpoint.EndPointI;

/**
 * 
 * @author wuzhen
 *         <p>
 *         Submit the login email and password
 */
public class AutoLoginHandler extends UiHandlerSupport implements EventHandlerI<AutoLoginRequireEvent> {

	/**
	 * @param c
	 */
	public AutoLoginHandler(ContainerI c) {
		super(c);
	}

	/*
	 * Jan 2, 2013
	 */
	@Override
	public void handle(AutoLoginRequireEvent ae) {
		//
		LoginControlI lc = this.getControl(LoginControlI.class, true);
		EndPointI ep = this.getEndpoint();
		AutoLoginHandler.autoLogin(ep, ae.getSource());

	}

	public static void autoLogin(EndPointI endpoint, UiObjectI esource) {
		ObjectPropertiesData req = new ObjectPropertiesData();

		// this submit

		AccountsLDW accs = AccountsLDW.getInstance();
		RegisteredAccountLDW acc1 = accs.getRegistered();
		AnonymousAccountLDW acc2 = accs.getAnonymous();
		if (acc1.isValid()) {//registered user is available
			req.setProperty("isSaved", (true));
			req.setProperty("type", ("registered"));
			req.setProperty("email", (acc1.getEmail()));
			req.setProperty("password", (acc1.getPassword()));
		} else if (acc2.isValid()) {//anonymous user is available
			req.setProperty("isSaved", (true));
			req.setProperty("type", ("anonymous"));

			String accId = acc2.getAccountId();

			String password = acc2.getPassword();
			req.setProperty("accountId", (accId));
			req.setProperty("password", (password));

		} else {// has not saved account,create it first and then call this
				// submit again.
			MsgWrapper msg = new MsgWrapper(Path.valueOf("/signup/anonymous"));
			endpoint.sendMessage(msg);
			return;
		}

		endpoint.auth(req);
	}

}
