/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 19, 2012
 */
package com.graphscape.gwt.commons.drag.event;

import com.graphscape.gwt.commons.drag.DragableI;
import com.graphscape.gwt.commons.drag.DraggerI;
import com.graphscape.gwt.commons.drag.event.DragEvent;
import com.graphscape.gwt.core.commons.Point;
import com.graphscape.gwt.core.core.Event;

/**
 * @author wu
 * 
 */
public abstract class DragEvent extends Event {

	public static Event.Type<DragEvent> TYPE = new Event.Type<DragEvent>("drag");

	protected DraggerI dragger;

	protected Point point;// mouse point

	/**
	 * @param type
	 */
	public DragEvent(Event.Type<? extends DragEvent> type, DragableI source,
			DraggerI db, Point point) {
		super(type, source);
		this.dragger = db;
		this.point = point;
	}

	public DraggerI getDragger() {
		return dragger;
	}

	public Point getPoint() {
		return point;
	}

	@Override
	public String toString() {
		return "Event,class:" + this.getClass().getName() + ",point:"+this.point;
	}
}
