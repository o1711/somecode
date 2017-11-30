/**
 * Jun 30, 2012
 */
package com.graphscape.gwt.commons.widget.event;

import com.graphscape.gwt.core.core.Event;
import com.graphscape.gwt.core.core.UiObjectI;

/**
 * @author wu
 * 
 */
public class SelectEvent extends Event {

	public static Type<SelectEvent> TYPE = new Type<SelectEvent>("select");

	private boolean selected;

	/** */
	public SelectEvent(UiObjectI source, boolean sel) {
		super(TYPE, source);
		this.selected = sel;
	}

	public boolean isSelected() {
		return selected;
	}
}
