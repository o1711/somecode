/**
 * Jun 30, 2012
 */
package com.graphscape.gwt.core;

import java.util.List;

import com.graphscape.gwt.core.CompositeI;
import com.graphscape.gwt.core.core.WidgetI;

/**
 * @author wu
 * 
 */
public interface CompositeI extends WidgetI {

	public CompositeI child(WidgetI w);

	public List<WidgetI> getChildWidgetList();

}
