/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 26, 2012
 */
package com.graphscape.gwt.commons.event;

import com.graphscape.gwt.core.core.UiObjectI;
import com.graphscape.gwt.core.endpoint.UserInfo;

/**
 * @author wu
 * @deprecated
 */
public class RegisterUserLoginEvent extends UserLoginEvent {

	public static final Type<RegisterUserLoginEvent> TYPE = new Type<RegisterUserLoginEvent>(UserLoginEvent.TYPE,"registered");

	/**
	 * @param type
	 */
	public RegisterUserLoginEvent(UiObjectI src, UserInfo ui) {
		super(TYPE, src, ui);
	}

}
