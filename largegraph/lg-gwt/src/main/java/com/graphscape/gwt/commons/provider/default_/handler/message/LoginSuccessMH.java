/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 2, 2013
 */
package com.graphscape.gwt.commons.provider.default_.handler.message;

import com.graphscape.gwt.commons.ErrorCodes;
import com.graphscape.gwt.commons.frwk.login.LoginViewI;
import com.graphscape.gwt.commons.mvc.support.UiHandlerSupport;
import com.graphscape.gwt.commons.provider.default_.frwk.login.AccountsLDW;
import com.graphscape.gwt.commons.provider.default_.frwk.login.AnonymousAccountLDW;
import com.graphscape.gwt.commons.provider.default_.frwk.login.RegisteredAccountLDW;
import com.graphscape.gwt.commons.provider.default_.handler.action.AutoLoginHandler;
import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.UiException;
import com.graphscape.gwt.core.UiResponse;
import com.graphscape.gwt.core.data.ErrorInfosData;
import com.graphscape.gwt.core.data.message.MessageData;
import com.graphscape.gwt.core.data.property.ObjectPropertiesData;
import com.graphscape.gwt.core.event.EndpointMessageEvent;
import com.graphscape.gwt.core.message.MessageHandlerI;

/**
 * @author wu
 * 
 */
public class LoginSuccessMH extends UiHandlerSupport implements MessageHandlerI<EndpointMessageEvent> {

	/**
	 * @param c
	 */
	public LoginSuccessMH(ContainerI c) {
		super(c);
	}

	/*
	 * Jan 2, 2013
	 */
	@Override
	public void handle(EndpointMessageEvent t) {
		MessageData res = t.getMessage();

		AccountsLDW accs = AccountsLDW.getInstance();

		Boolean isSaved = res.getBoolean("isSaved", true);
		if (isSaved) {// // if by auto login,do nothing
			return;
		}
		String type = res.getString("type");

		// type must be registed.

		// is click by user.
		// not saved info,but user provided info,so notify user this
		// error.

		LoginViewI lv = this.getRootView().find(LoginViewI.class, true);
		if (!lv.isSavingAccount()) {
			return;
		}
		String email = res.getString("email", true);
		String password = res.getString("password", true);

		RegisteredAccountLDW acc1 = accs.getRegistered();
		acc1.save(email, password);
	}
}
