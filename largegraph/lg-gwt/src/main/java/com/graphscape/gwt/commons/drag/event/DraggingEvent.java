/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 19, 2012
 */
package com.graphscape.gwt.commons.drag.event;

import com.graphscape.gwt.commons.drag.DragableI;
import com.graphscape.gwt.commons.drag.DraggerI;
import com.graphscape.gwt.commons.drag.event.DragEvent;
import com.graphscape.gwt.commons.drag.event.DraggingEvent;
import com.graphscape.gwt.core.commons.Point;
import com.graphscape.gwt.core.core.Event;

/**
 * @author wu
 * 
 */
public class DraggingEvent extends DragEvent {

	public static Event.Type<DraggingEvent> TYPE = new Event.Type<DraggingEvent>(
			DragEvent.TYPE, "dragging");

	/**
	 * @param type
	 */
	public DraggingEvent(DragableI source, DraggerI db, Point p) {
		super(TYPE, source, db, p);
	}

}
