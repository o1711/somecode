/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 2, 2013
 */
package com.graphscape.gwt.commons.provider.default_.handler.action;

import com.graphscape.gwt.commons.event.ActionEvent;
import com.graphscape.gwt.commons.handler.ActionHandlerSupport;
import com.graphscape.gwt.commons.provider.default_.frwk.login.AccountsLDW;
import com.graphscape.gwt.commons.provider.default_.frwk.login.RegisteredAccountLDW;
import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.endpoint.UserInfo;

/**
 * @author wu
 * @deprecated
 */
public class LogoutAP extends ActionHandlerSupport {

	/**
	 * @param c
	 */
	public LogoutAP(ContainerI c) {
		super(c);

	}

	/**
	 * @param c
	 */

	/*
	 * Jan 2, 2013
	 */
	@Override
	public void handle(ActionEvent ae) {
		UserInfo ui = this.getEndpoint().getUserInfo();
		if (!ui.isAnonymous()) {
			RegisteredAccountLDW ral = AccountsLDW.getInstance().getRegistered();
			if (ral.isValid()) {
				ral.invalid();
			}
		}

		this.getEndpoint().logout();//

	}

}
