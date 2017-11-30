/**
 *  Jan 31, 2013
 */
package com.graphscape.gwt.commons.frwk;

import com.graphscape.gwt.commons.mvc.ViewI;
import com.graphscape.gwt.core.commons.Path;
import com.graphscape.gwt.core.core.WidgetI;

/**
 * @author wuzhen
 * 
 */
public interface BodyViewI extends ViewI {

	public <T extends WidgetI> T getItem(Path path, boolean force);

	public <T extends WidgetI> T addItem(Path path, T w);
	
	public void select(Path path);
	
	public void tryCloseItem(Path path);
	
	//TODO provide new interface.
	public void setTitleOfItem(Path path, String title, boolean force);
	
	public <T extends WidgetI> T getOrCreateItem(Path path, com.graphscape.gwt.commons.CreaterI<T> crt);

	public <T extends WidgetI> T getOrCreateItem(Path path, com.graphscape.gwt.commons.CreaterI<T> crt,boolean select);
	
	public void closeAllItems();

}
