/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 15, 2012
 */
package com.graphscape.gwt.core.support;

import com.graphscape.gwt.core.core.Event.EventHandlerI;
import com.graphscape.gwt.core.event.ModelValueEvent;

/**
 * @author wu
 * 
 */
public abstract class ModelValueHandler<T> implements EventHandlerI<ModelValueEvent> {

	private T defValue;

	public ModelValueHandler() {

	}

	public ModelValueHandler(T def) {
		this.defValue = def;
	}

	@Override
	public void handle(ModelValueEvent e) {
		Object value = e.getValue(this.defValue);

		this.handleValue((T) value);
	}

	public abstract void handleValue(T value);

}
