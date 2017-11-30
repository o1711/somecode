/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 17, 2012
 */
package com.fs.uiclient.api.gwt.client.uexp;

import java.util.List;

import com.fs.uicore.api.gwt.client.ModelI;

/**
 * @author wu
 * 
 */
public interface UserExpListModelI extends ModelI {

	// user.
	// when new exp is created,this value is set and the control will get the
	// content from server side.
	// when one selected.
	/**
	 * see ExpSearchControl.onUserExpSelected() see ExpSearchAP of
	 * ExpSearchControl
	 */
	public static final Location L_SELECTED_EXP_ID = Location.valueOf("selectedExpId");

	public void addUserExp(UserExpModel uem);

	public UserExpModel getSelected(boolean force);

	public UserExpModel getUserExp(String id, boolean force);// id /name?

	public void select(String expId);// the current user exp,use select this
										// one,for search or open it.
	public List<UserExpModel> getUelist() ;

	public void reset();
}
