package com.graphscape.gwt.commons.provider.default_.handler.action;

import com.graphscape.gwt.commons.event.ActionEvent;
import com.graphscape.gwt.commons.frwk.login.LoginControlI;
import com.graphscape.gwt.commons.frwk.login.LoginViewI;
import com.graphscape.gwt.commons.frwk.password.PasswordResetViewI;
import com.graphscape.gwt.commons.handler.ActionHandlerSupport;
import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.MsgWrapper;
import com.graphscape.gwt.core.commons.Path;

/**
 * 
 * @author wuzhen
 *         <p>
 *         Request to reset password.
 */
public class PasswordForgotAP extends ActionHandlerSupport {

	/**
	 * @param c
	 */
	public PasswordForgotAP(ContainerI c) {
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
		//LoginControlI lc = this.getControl(LoginControlI.class, true);
		//PasswordResetViewI pv = lc.openPasswordResetView();
		//pv.clearErrorInfo();
		
		MsgWrapper req = this.newRequest(Path.valueOf("password/forgot"));

		String email = lm.getEmail();
		req.setPayload("email", email);

		this.sendMessage(req);
	}

}
