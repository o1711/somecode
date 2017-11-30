/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 5, 2012
 */
package com.graphscape.gwt.core.event;

import com.graphscape.gwt.core.core.Event;
import com.graphscape.gwt.core.core.UiObjectI;

/**
 * @author wu
 * 
 */
public class HideEvent extends Event {

	public static final Type<HideEvent> TYPE = new Type<HideEvent>("hide");

	/**
	 * @param type
	 */
	public HideEvent(UiObjectI src) {
		super(TYPE, src);
	}

}
