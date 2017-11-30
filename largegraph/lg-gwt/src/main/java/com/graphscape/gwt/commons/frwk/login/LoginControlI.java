package com.graphscape.gwt.commons.frwk.login;

import com.graphscape.gwt.commons.frwk.password.PasswordResetViewI;
import com.graphscape.gwt.commons.mvc.ControlI;

public interface LoginControlI extends ControlI {

	/**
	 * @return
	 */
	public LoginViewI openLoginView(boolean show);
	
	public PasswordResetViewI openPasswordResetView();

}
