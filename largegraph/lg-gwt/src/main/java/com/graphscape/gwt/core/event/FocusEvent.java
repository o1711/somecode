/**
 * Jun 29, 2012
 */
package com.graphscape.gwt.core.event;

import com.graphscape.gwt.core.core.Event;
import com.graphscape.gwt.core.core.UiObjectI;

/**
 * @author wu
 * 
 */
public class FocusEvent extends Event {

	public static final Type<FocusEvent> TYPE = new Type<FocusEvent>("focus");

	/** */
	public FocusEvent(UiObjectI src) {
		super(TYPE, src);
	}

}
