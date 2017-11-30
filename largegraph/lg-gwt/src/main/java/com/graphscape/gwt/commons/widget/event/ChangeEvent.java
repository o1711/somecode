/**
 * Jun 30, 2012
 */
package com.graphscape.gwt.commons.widget.event;

import com.graphscape.gwt.commons.widget.EditorI;
import com.graphscape.gwt.core.core.Event;

/**
 * @author wu
 * 
 */
public class ChangeEvent extends Event {

	public static Type<ChangeEvent> TYPE = new Type<ChangeEvent>("change");

	/** */
	public ChangeEvent(EditorI source) {
		super(TYPE, source);
	}

	public EditorI getEditor() {
		return (EditorI) this.source;
	}

	public <T> T getData() {
		return (T) this.getEditor().getData();
	}

}
