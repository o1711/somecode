/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 3, 2012
 */
package com.graphscape.gwt.commons.widget.menu;

import com.graphscape.gwt.commons.widget.menu.MenuItemWI;
import com.graphscape.gwt.commons.widget.menu.MenuWI;
import com.graphscape.gwt.core.CompositeI;
import com.graphscape.gwt.core.core.WidgetI;

/**
 * @author wu
 *         <p>
 *         Context menu,pop up view.
 */
public interface MenuWI extends CompositeI {

	public MenuItemWI addItem(String name);

	public MenuItemWI addItem(String name, MenuWI subm);

	public MenuItemWI getItem(String name);

	public void openBy(WidgetI src);

	public void close();

}
