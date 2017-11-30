/**
 *  Dec 27, 2012
 */
package com.graphscape.gwt.commons.widget;

import com.graphscape.gwt.commons.widget.event.SelectEvent;
import com.graphscape.gwt.core.core.Event.EventHandlerI;

/**
 * @author wuzhen
 * 
 */
public interface SelectableI {

	public boolean isSelected();

	public void select();

	public void addSelectEventHandler(EventHandlerI<SelectEvent> eh);
}
