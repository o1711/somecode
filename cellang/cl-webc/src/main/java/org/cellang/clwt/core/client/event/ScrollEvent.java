/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 5, 2012
 */
package org.cellang.clwt.core.client.event;

import org.cellang.clwt.core.client.lang.Point;
import org.cellang.clwt.core.client.lang.WebObject;

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
	public ScrollEvent(Point topLeft, WebObject src) {
		super(TYPE, src);
		this.topLeft = topLeft;
	}

	public Point getTopLeft() {
		return topLeft;
	}
}
