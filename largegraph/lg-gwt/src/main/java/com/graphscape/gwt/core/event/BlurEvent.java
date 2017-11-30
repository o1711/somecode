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
public class BlurEvent extends Event {

	public static final Type<BlurEvent> TYPE = new Type<BlurEvent>("blur");

	/** */
	public BlurEvent(UiObjectI src) {
		super(TYPE, src);
	}

}
