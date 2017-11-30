/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 3, 2012
 */
package com.fs.uicommons.api.gwt.client.widget.tab;

import java.util.List;

import com.fs.uicore.api.gwt.client.CompositeI;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.core.WidgetI;

/**
 * @author wu
 * 
 */
public interface TabberWI extends CompositeI {

	public static final String PK_IS_VERTICAL = "isVertical";
	
	public static final String PK_IS_CLOSABLE = "isClosable";
	
	public static final String PK_IS_REVERSE = "isReverse";

	public TabWI getSelected(boolean force);
	
	public List<TabWI> getTabList() ;

	public TabWI addTab(Path name);

	public TabWI addTab(Path name, WidgetI content);

	public TabWI addTab(Path name, WidgetI content, boolean sel);

	public TabWI getTab(Path name, boolean force);

	public boolean remove(Path path);
	
	public void removeAll();
	
}
