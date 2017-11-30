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
public class ClickEvent extends Event {

	public static final Type<ClickEvent> TYPE = new Type<ClickEvent>("click");

	/** */
	public ClickEvent(UiObjectI src) {
		super(TYPE, src);
	}

}
