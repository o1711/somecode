/**
 * Jun 30, 2012
 */
package com.graphscape.gwt.commons.widget.support;

import com.google.gwt.user.client.Element;
import com.graphscape.gwt.commons.AdjusterI;
import com.graphscape.gwt.commons.widget.support.AdjustersHelper;
import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.UiException;
import com.graphscape.gwt.core.commons.UiPropertiesI;
import com.graphscape.gwt.core.core.UiObjectI;
import com.graphscape.gwt.core.core.WidgetI;
import com.graphscape.gwt.core.core.Event.EventHandlerI;
import com.graphscape.gwt.core.event.ClickEvent;
import com.graphscape.gwt.core.support.WidgetBase;

/**
 * @author wu
 * 
 */
public class WidgetSupport extends WidgetBase {

	protected AdjustersHelper adjustersHelper;

	public WidgetSupport(ContainerI c, String name, Element ele) {
		super(c, name, ele);

	}

	public WidgetSupport(ContainerI c, String name, Element ele, UiPropertiesI<Object> pts) {
		super(c, name, ele, pts);
	}

	@Override
	public UiObjectI parent(UiObjectI parent) {
		WidgetI old = (WidgetI) this.parent;

		if (parent != null && !(parent instanceof WidgetI)) {
			throw new UiException("cannot set a none widget parent");
		}
		super.parent(parent);

		this.onSetParent(old, (WidgetI) parent);

		return this;
	}

	protected void onSetParent(WidgetI old, WidgetI newP) {

		if (old != null && newP != null) {
			throw new UiException("changing parent not suported, current parent:" + old + ",new parent:"
					+ newP + " for widget:" + this + "");
		}

		if (old != null && old.isAttached()) {
			this.detach();
		}

		if (newP != null && newP.isAttached()) {
			this.attach();
		}

	}

	@Override
	public boolean isAttached() {
		return attached;
	}

	// @Override
	public AdjusterI addAdjuster(String name, EventHandlerI<ClickEvent> eh) {
		AdjusterI rt = this.addAdjuster(name);
		rt.addClickHandler(eh);
		return rt;
	}

	// @Override
	public AdjusterI addAdjuster(String name) {
		if (this.adjustersHelper == null) {
			this.adjustersHelper = new AdjustersHelper(this.container);//
			this.adjustersHelper.parent(this);
		}

		AdjusterI rt = this.adjustersHelper.addAdjuster(name);

		return rt;
	}

	protected String localized(String key) {
		return this.getClient(true).localized(key);
	}

	@Override
	public void addChild(UiObjectI c) {
		super.addChild(c);
		if (c instanceof AdjusterI) {// add to the element
			// this.processAddAdjuster((AdjusterI)c);
		}
	}

}
