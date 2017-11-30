package com.graphscape.gwt.commons.frwk.login;

import com.graphscape.gwt.core.ModelI;

public interface LoginModelI extends ModelI {

	public static String HEADER_ITEM_LOGIN = "login";//

	public boolean isSavingAccount();// auto auth for next, save successful
	// account/password.

	public String getEmail();

	public String getPassword();

}
