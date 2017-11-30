/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 6, 2012
 */
package com.graphscape.gwt.commons.widget.bar;

import com.graphscape.gwt.commons.Position;
import com.graphscape.gwt.core.ModelI;
import com.graphscape.gwt.core.core.WidgetI;

/**
 * @author wu
 * 
 */
public interface BarWidgetI extends WidgetI{

	public static final Position P_LEFT = Position.valueOf("left");

	public static final Position P_RIGHT = Position.valueOf("right");

	public static final Position P_CENTER = Position.valueOf("center");

	public void addItem(Position pos, WidgetI cw);

}
