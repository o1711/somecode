/**
 * All right is from Author of the file.
 * Any usage of the code must be authorized by the the auther.
 * If not sure the detail of the license,please distroy the copies immediately.  
 * Nov 15, 2012
 */
package com.graphscape.gwt.commons;

import com.graphscape.gwt.core.core.ElementObjectI;
import com.graphscape.gwt.core.core.WidgetI;
import com.graphscape.gwt.core.core.Event.EventHandlerI;
import com.graphscape.gwt.core.event.ClickEvent;

/**
 * @author wuzhen
 * @deprecated to be rethink.
 */
public interface AdjusterI extends ElementObjectI {

	public WidgetI getOwner();

	public void addClickHandler(EventHandlerI<ClickEvent> eh);
}
