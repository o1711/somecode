/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 26, 2012
 */
package com.graphscape.gwt.commons.event;

import com.graphscape.gwt.core.core.Event;
import com.graphscape.gwt.core.core.UiObjectI;
import com.graphscape.gwt.core.endpoint.UserInfo;

/**
 * @author wu
 * @deprecated
 */
public class UserLoginEvent extends Event {

	public static final Type<UserLoginEvent> TYPE = new Type<UserLoginEvent>("user-login");

	private UserInfo userInfo;

	/**
	 * @param type
	 */
	public UserLoginEvent(UiObjectI src, UserInfo ui) {
		this(TYPE, src, ui);
	}

	public UserLoginEvent(Type<? extends UserLoginEvent> type, UiObjectI src, UserInfo ui) {
		super(type, src);
		this.userInfo = ui;
	}

	/**
	 * @return the userInfo
	 */
	public UserInfo getUserInfo() {
		return userInfo;
	}

}
