/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 3, 2012
 */
package com.graphscape.gwt.commons.widget.tab;

import com.graphscape.gwt.commons.widget.SelectableI;
import com.graphscape.gwt.commons.widget.panel.PanelWI;
import com.graphscape.gwt.core.core.WidgetI;

/**
 * @author wu
 * 
 */
public interface TabWI extends WidgetI, SelectableI {

	public String getName();
	
	public void setText(boolean i18n, String text);

	public PanelWI getPanel();
	
	public WidgetI getManaged();

	public void close();

}
