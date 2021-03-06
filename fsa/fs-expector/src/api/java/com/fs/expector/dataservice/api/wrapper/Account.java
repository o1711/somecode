/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 28, 2012
 */
package com.fs.expector.dataservice.api.wrapper;

import com.fs.dataservice.api.core.meta.DataSchema;
import com.fs.dataservice.api.core.wrapper.NodeWrapper;
import com.fs.expector.dataservice.api.NodeTypes;

/**
 * @author wu
 * 
 */
public class Account extends NodeWrapper {

	public static final String PASSWORD = "password";

	public static final String NICK = "nick";

	public static final String TYPE = "type";

	public static final String TYPE_ANONYMOUS = "anonymous";

	public static final String TYPE_EXTERNAL = "external";

	public static final String TYPE_REGISTERED = "registered";

	/**
	 * @param pts
	 */
	public Account() {
		super(NodeTypes.ACCOUNT);
	}

	public static void config(DataSchema cfs) {
		cfs.addConfig(NodeTypes.ACCOUNT, Account.class).field(PASSWORD).field(NICK).field(TYPE);

	}

	public Account applayFrom(SignupRequest sr, SignupConfirm sc) {

		return this;
	}

	public void setPassword(String password) {
		this.setProperty(PASSWORD, password);
	}

	public String getPassword() {
		return (String) this.target.getProperty(PASSWORD);
	}

	/**
	 * Oct 29, 2012
	 */
	public String getNick() {
		//
		return (String) this.target.getProperty(NICK);

	}

	/**
	 * Nov 2, 2012
	 */
	public void setNick(String nick2) {
		this.setProperty(NICK, nick2);
	}

	public void setType(String type) {
		this.setProperty(TYPE, type);
	}

	public String getType() {
		return (String) this.getProperty(TYPE);
	}

	public boolean isType(String type) {
		return type.equals(this.getType());
	}

}
