/**
 * Jul 7, 2012
 */
package com.graphscape.gwt.core.event;

import com.graphscape.gwt.core.ModelI;
import com.graphscape.gwt.core.ModelI.Location;
import com.graphscape.gwt.core.ModelI.ValueWrapper;
import com.graphscape.gwt.core.util.ObjectUtil;

/**
 * @author wu
 * @deprecated use model update event.
 */
public class ModelValueEvent extends ModelEvent {
	public static Type<ModelValueEvent> TYPE = new Type<ModelValueEvent>(
			ModelEvent.TYPE, "value");

	protected Location location;

	protected ValueWrapper valueWrapper;

	public ModelValueEvent(ModelI m, Location loc, ValueWrapper vw) {
		this(m, loc, vw, null);
	}

	/** */
	public ModelValueEvent(ModelI m, Location loc, ValueWrapper vw, String rep) {
		super(TYPE, m, rep);
		this.location = loc;
		this.valueWrapper = vw;
	}

	public static ModelValueEvent valueOf(ModelI m, Location loc,
			ValueWrapper vw) {

		ModelValueEvent rt = new ModelValueEvent(m, loc, vw);

		return rt;
	}

	public Location getLocation() {
		return location;
	}

	public ValueWrapper getValueWrapper() {
		return valueWrapper;
	}

	public Object getValue() {
		return this.valueWrapper.getValue();
	}

	public <T> T getValue(T def) {
		T rt = this.valueWrapper.getValue(def);
		return rt;
	}

	public boolean isValue(Object v) {
		return ObjectUtil.nullSafeEquals(v, this.getValue());
	}
}
