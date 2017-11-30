/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 9, 2012
 */
package com.graphscape.gwt.commons.provider.default_.frwk.login;

import com.graphscape.gwt.commons.ldata.LocalData;
import com.graphscape.gwt.commons.provider.default_.frwk.login.AbstractAccountLDW;

/**
 * @author wu
 * 
 */
public class RegisteredAccountLDW extends AbstractAccountLDW {

	private static String K_EMAIL = "email";
	
	public RegisteredAccountLDW(LocalData localData) {
		super(localData);
	}

	public String getEmail() {
		return this.getValue(K_EMAIL);
	}

	public void save(String email, String password) {
		this.setValue(K_EMAIL, email);
		this.setPassword(password);
		this.valid(true);
	}

}
