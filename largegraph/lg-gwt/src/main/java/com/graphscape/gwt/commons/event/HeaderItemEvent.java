/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 2, 2013
 */
package com.graphscape.gwt.commons.event;

import com.graphscape.gwt.core.commons.Path;
import com.graphscape.gwt.core.core.Event;
import com.graphscape.gwt.core.core.UiObjectI;

/**
 * @author wu
 * 
 */
public class HeaderItemEvent extends Event {

	public static final Type<HeaderItemEvent> TYPE = new Type<HeaderItemEvent>("header-item");

	/**
	 * @param type
	 */
	public HeaderItemEvent(UiObjectI src, Path path) {
		super(TYPE, src, path);
	}

}
