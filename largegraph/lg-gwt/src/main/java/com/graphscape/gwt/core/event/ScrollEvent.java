/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 5, 2012
 */
package com.graphscape.gwt.core.event;

import com.graphscape.gwt.core.commons.Point;
import com.graphscape.gwt.core.core.Event;
import com.graphscape.gwt.core.core.UiObjectI;

/**
 * @author wu
 * 
 */
public class ScrollEvent extends Event {

	public static final Type<ScrollEvent> TYPE = new Type<ScrollEvent>("scroll");

	private Point topLeft;

	/**
	 * @param type
	 */
	public ScrollEvent(Point topLeft, UiObjectI src) {
		super(TYPE, src);
		this.topLeft = topLeft;
	}

	public Point getTopLeft() {
		return topLeft;
	}
}
