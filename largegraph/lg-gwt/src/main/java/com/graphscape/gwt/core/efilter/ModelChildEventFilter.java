package com.graphscape.gwt.core.efilter;

import com.graphscape.gwt.core.ModelI;
import com.graphscape.gwt.core.core.Event;
import com.graphscape.gwt.core.efilter.ModelEventFilter;
import com.graphscape.gwt.core.event.ModelChildEvent;
import com.graphscape.gwt.core.reflect.InstanceOf;

public class ModelChildEventFilter extends ModelEventFilter implements
		Event.FilterI {

	private Class<? extends ModelI> childClass;

	private Boolean isAdd;

	private String name;

	public ModelChildEventFilter(Class<? extends ModelI> childClass,
			Boolean isAdd) {
		this(childClass, isAdd, null);
	}

	public ModelChildEventFilter(Class<? extends ModelI> childClass,
			Boolean isAdd, String name) {
		super(ModelChildEvent.TYPE);
		this.childClass = childClass;
		this.isAdd = isAdd;
		this.name = name;
	}

	@Override
	public <T extends Event> T filter(Event e) {
		if (null == super.filter(e)) {
			return null;
		}
		ModelChildEvent mve = (ModelChildEvent) e;

		ModelI cm = mve.getChild();

		mve.isAdd();

		if (this.childClass == null
				|| InstanceOf.isInstance(this.childClass, cm)//
				&& (this.isAdd == null || this.isAdd == mve.isAdd())//
				&& (this.name == null || this.name.equals(cm.getName()))//
		) {

			return (T) mve;

		}
		return null;

	}

	/*
	 * Nov 8, 2012
	 */
	@Override
	public String toString() {
		//
		return super.toString() + ",childClass:" + this.childClass + ",isAdd:"
				+ isAdd + ",name:" + this.name;
	}

}
