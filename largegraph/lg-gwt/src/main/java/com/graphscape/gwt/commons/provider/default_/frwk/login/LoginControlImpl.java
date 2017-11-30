/**
 *  Jan 31, 2013
 */
package com.graphscape.gwt.commons.provider.default_.frwk.login;

import com.graphscape.gwt.commons.Constants;
import com.graphscape.gwt.commons.frwk.BodyViewI;
import com.graphscape.gwt.commons.frwk.login.LoginControlI;
import com.graphscape.gwt.commons.frwk.login.LoginViewI;
import com.graphscape.gwt.commons.frwk.password.PasswordResetViewI;
import com.graphscape.gwt.commons.mvc.support.ControlSupport;
import com.graphscape.gwt.commons.provider.default_.frwk.login.LoginView;
import com.graphscape.gwt.commons.provider.default_.frwk.password.PasswordResetView;
import com.graphscape.gwt.core.ContainerI;

/**
 * @author wuzhen
 * 
 */
public class LoginControlImpl extends ControlSupport implements LoginControlI {

	/**
	 * @param name
	 */
	public LoginControlImpl(ContainerI c, String name) {
		super(c, name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.commons.uicommons.api.gwt.client.frwk.login.LoginControlI#openLoginView()
	 */
	@Override
	public LoginViewI openLoginView(boolean show) {

		// TODO creater
		BodyViewI bv = this.getBodyView();
		LoginView lv = bv.getItem(Constants.LOGIN_VIEW, false);
		if (lv == null) {
			lv = new LoginView(this.getContainer(), "login");

			bv.addItem(Constants.LOGIN_VIEW, lv);
		}
		if(show){
			bv.select(Constants.LOGIN_VIEW);//			
		}
		return lv;
	}

	/*
	 * Mar 31, 2013
	 */
	@Override
	public PasswordResetViewI openPasswordResetView() {
		// TODO creater
		BodyViewI bv = this.getBodyView();
		PasswordResetViewI lv = bv.getItem(Constants.PASSWORDRESET_VIEW, false);
		if (lv == null) {
			lv = new PasswordResetView(this.getContainer());

			bv.addItem(Constants.PASSWORDRESET_VIEW, lv);
		}
		bv.select(Constants.PASSWORDRESET_VIEW);//
		return lv;
	}

}
