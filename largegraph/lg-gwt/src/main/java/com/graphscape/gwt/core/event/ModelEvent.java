/**
 * Jul 7, 2012
 */
package com.graphscape.gwt.core.event;

import com.graphscape.gwt.core.ModelI;
import com.graphscape.gwt.core.util.ObjectUtil;

/**
 * @author wu
 * @deprecated
 */
public class ModelEvent extends LocalEvent {
	public static Type<ModelEvent> TYPE = new Type<ModelEvent>("model");

	protected String replay;

	public ModelEvent(Type<? extends ModelEvent> type, ModelI m) {
		this(type, m, null);
	}

	public ModelEvent(Type<? extends ModelEvent> type, ModelI m, String rep) {
		super(type, m);
		this.replay = rep;
	}

	public ModelI getModel() {
		return (ModelI) this.source;
	}

	public boolean isReplay() {
		return this.replay != null;
	}

	public boolean isReplay(String key) {
		return ObjectUtil.nullSafeEquals(this.replay, key);
	}

	public <T extends ModelI> T getModel(Class<T> cls) {
		return (T) this.getModel();
	}
}
