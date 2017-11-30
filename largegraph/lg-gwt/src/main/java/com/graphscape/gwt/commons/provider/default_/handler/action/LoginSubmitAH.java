package com.graphscape.gwt.commons.provider.default_.handler.action;

import com.graphscape.gwt.commons.event.ActionEvent;
import com.graphscape.gwt.commons.frwk.login.LoginViewI;
import com.graphscape.gwt.commons.handler.ActionHandlerSupport;
import com.graphscape.gwt.commons.provider.default_.frwk.login.AccountsLDW;
import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.data.property.ObjectPropertiesData;

/**
 * 
 * @author wuzhen
 *         <p>
 *         Submit the login email and password
 */
public class LoginSubmitAH extends ActionHandlerSupport {

	/**
	 * @param c
	 */
	public LoginSubmitAH(ContainerI c) {
		super(c);
	}

	/*
	 * Jan 2, 2013
	 */
	@Override
	public void handle(ActionEvent ae) {
		//

		LoginViewI lm = this.findView(LoginViewI.class, true);
		lm.clearErrorInfo();//
		ObjectPropertiesData req = new ObjectPropertiesData();

		// this submit

		req.setProperty("type", ("registered"));
		req.setProperty("isSaved", Boolean.FALSE);

		String email = lm.getEmail();

		String password = lm.getPassword();
		req.setProperty("email", (email));
		req.setProperty("password", (password));
		
		this.getEndpoint().auth(req);
	}

}
