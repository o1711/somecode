/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 10, 2013
 */
package com.fs.uicommons.api.gwt.client;

import com.fs.uicommons.api.gwt.client.event.ActionEvent;
import com.fs.uicore.api.gwt.client.commons.Path;

/**
 * @author wu
 *         <p>
 *         Action is the bridge between view and the other
 *         parts:model/control/handler.
 */
public class Actions {

	/** ACTION ROOT **/
	public static final Path ACTION = ActionEvent.TYPE.getAsPath();

	/** LOGIN **/
	public static final Path A_LOGIN = ACTION.getSubPath("login");
	
	/** PASSWORD**/
	public static final Path A_PASSWORD = ACTION.getSubPath("password");
	
	// create anonymous
	// account.

	public static final Path A_LOGIN_LOGOUT = A_LOGIN.getSubPath("logout");
	// logout and open login
	// view?.

	public static final Path A_LOGIN_SUBMIT = A_LOGIN.getSubPath("submit");
	
	public static final Path A_LOGIN_SUBMIT_SUCCESS = A_LOGIN_SUBMIT.getSubPath("success");
	
	public static final Path A_LOGIN_FACEBOOK = A_LOGIN.getSubPath("facebook");

	public static final Path A_PASSWORD_FORGOT = A_PASSWORD.getSubPath("forgot");
	
	public static final Path A_PASSWORD_RESET = A_PASSWORD.getSubPath("reset");

	/** GCHAT **/

	public static final Path A_GCHAT = ACTION.getSubPath("gchat");

	public static final Path A_GCHAT_JOIN = A_GCHAT.getSubPath("join");

	public static final Path A_GCHAT_SEND = A_GCHAT.getSubPath("send");

}
