/**
 * Jun 13, 2012
 */
package com.graphscape.gwt.core.support;

import com.google.gwt.user.client.Element;
import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.WidgetFactoryI;
import com.graphscape.gwt.core.commons.UiPropertiesI;
import com.graphscape.gwt.core.core.WidgetI;
import com.graphscape.gwt.core.event.HideEvent;

/**
 * @author wuzhen
 * 
 */
public abstract class WidgetBase extends ElementObjectSupport implements WidgetI {

	protected WidgetFactoryI factory;

	protected WidgetBase(ContainerI c, String name, Element element) {
		this(c, name, element, null);
	}

	protected WidgetBase(ContainerI c, String name, Element element, UiPropertiesI<Object> pts) {
		super(c, name, element, pts);
		this.factory = c.get(WidgetFactoryI.class, true);
	}

	@Override
	protected String getClassNamePrefix() {
		return "wgt-";
	}

	@Override
	public void setVisible(boolean vis) {
		super.setVisible(vis);
		if (!this.visible) {
			new HideEvent(this).dispatch();
		}
	}

}
