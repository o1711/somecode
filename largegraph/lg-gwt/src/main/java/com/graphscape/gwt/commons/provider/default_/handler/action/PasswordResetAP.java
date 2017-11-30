/**
 * All right is from Author of the file,to be explained in comming days.
 * Mar 31, 2013
 */
package com.graphscape.gwt.commons.provider.default_.handler.action;

import com.graphscape.gwt.commons.event.ActionEvent;
import com.graphscape.gwt.commons.frwk.login.LoginControlI;
import com.graphscape.gwt.commons.frwk.password.PasswordResetViewI;
import com.graphscape.gwt.commons.handler.ActionHandlerSupport;
import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.MsgWrapper;
import com.graphscape.gwt.core.commons.Path;

/**
 * @author wu
 *
 */
public class PasswordResetAP extends ActionHandlerSupport{

	/**
	 * @param c
	 */
	public PasswordResetAP(ContainerI c) {
		super(c);
	}

	/*
	 *Mar 31, 2013
	 */
	@Override
	public void handle(ActionEvent t) {
		LoginControlI lc = this.getControl(LoginControlI.class, true);
		PasswordResetViewI pv = lc.openPasswordResetView();
		pv.clearErrorInfo();
		MsgWrapper req = this.newRequest(Path.valueOf("password/reset"));
		String pfId = pv.getPfId();
		String np = pv.getNewPassword();
		req.setPayload("pfId", pfId);
		req.setPayload("newPassword", np);
		this.sendMessage(req);
		
	}

}
