/**
 * Jun 30, 2012
 */
package com.graphscape.gwt.commons.widget.event;

import com.graphscape.gwt.commons.widget.panel.PanelWI;
import com.graphscape.gwt.core.core.Event;

/**
 * @author wu
 * 
 */
public class ClosingEvent extends Event {

	public static Type<ClosingEvent> TYPE = new Type<ClosingEvent>("closing");

	/** */
	public ClosingEvent(PanelWI source) {
		super(TYPE, source);
	}

	public PanelWI getPanel() {
		return (PanelWI) this.source;
	}

}
