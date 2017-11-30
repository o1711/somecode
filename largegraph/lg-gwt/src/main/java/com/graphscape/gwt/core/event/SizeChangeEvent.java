/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 5, 2012
 */
package com.graphscape.gwt.core.event;

import com.graphscape.gwt.core.commons.Size;
import com.graphscape.gwt.core.core.Event;
import com.graphscape.gwt.core.core.UiObjectI;

/**
 * @author wu
 * 
 */
public class SizeChangeEvent extends Event {

	public static final Type<SizeChangeEvent> TYPE = new Type<SizeChangeEvent>("size-change");

	private Size size;

	/**
	 * @param type
	 */
	public SizeChangeEvent(Size size, UiObjectI src) {
		super(TYPE);
	}

	/**
	 * @return the size
	 */
	public Size getSize() {
		return size;
	}

}
