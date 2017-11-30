/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 24, 2012
 */
package com.graphscape.gwt.commons.frwk;

import com.graphscape.gwt.commons.frwk.BottomViewI;
import com.graphscape.gwt.commons.frwk.ConsoleViewI;
import com.graphscape.gwt.commons.frwk.HeaderViewI;
import com.graphscape.gwt.commons.frwk.login.LoginViewI;
import com.graphscape.gwt.commons.mvc.ControlI;
import com.graphscape.gwt.core.commons.Path;

/**
 * @author wu
 */
public interface FrwkControlI extends ControlI {

	public void open();

	public void tryRemoveHeaderItem(Path path);
	
	public void addHeaderItemIfNotExist(Path path);
	
	public void addHeaderItem(Path path);

	public void addHeaderItem(Path path, boolean left);

	public HeaderViewI getHeaderView();
	
	public BottomViewI getBottomView();

	public LoginViewI openLoginView(boolean show);
	
	public ConsoleViewI openConsoleView(boolean show);

}
