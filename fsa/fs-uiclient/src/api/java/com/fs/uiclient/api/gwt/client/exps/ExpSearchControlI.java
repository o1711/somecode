/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 17, 2012
 */
package com.fs.uiclient.api.gwt.client.exps;

import com.fs.uiclient.api.gwt.client.uexp.UserExpModel;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;

/**
 * @author wu
 * @deprecated user ExpSearchViewI
 */
public interface ExpSearchControlI extends ControlI {

	/**
	 * 
	 */
	public void search(UserExpModel ue, boolean showView);

	public void addOrUpdateExpItem(ExpItemModel ei);

	public void reset();

	public UserExpModel getExpId(boolean b);

	public String getPhrase(boolean b);
	

}
