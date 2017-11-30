/**
 * Jul 15, 2012
 */
package com.graphscape.gwt.commons.widget.support;

import java.util.List;

import com.google.gwt.user.client.Element;
import com.graphscape.gwt.commons.widget.support.WidgetSupport;
import com.graphscape.gwt.core.CompositeI;
import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.commons.UiPropertiesI;
import com.graphscape.gwt.core.core.ElementObjectI;
import com.graphscape.gwt.core.core.UiObjectI;
import com.graphscape.gwt.core.core.WidgetI;

/**
 * @author wu TODO rename to LayoutWidgetSupport
 */
public class LayoutSupport extends WidgetSupport implements CompositeI {
	public LayoutSupport(ContainerI c, Element ele) {
		this(c, null, ele);
	}

	public LayoutSupport(ContainerI c, String name, Element ele) {
		super(c, name, ele);
	}

	public LayoutSupport(ContainerI c, String name, Element ele, UiPropertiesI<Object> pts) {
		super(c, name, ele, pts);
	}

	@Override
	protected void processAddChildElementObject(ElementObjectI ceo) {
		this.onAddChild(this.element, ceo);
	}

	@Deprecated
	// use processAddChildElementObject
	protected void onAddChild(Element pe, ElementObjectI ceo) {
		Element ele = ceo.getElement();
		if(ele.hasParentElement()){
			return;
		}
		pe.appendChild(ele);//
	}

	/* */
	@Override
	public CompositeI child(WidgetI w) {
		w.parent(this);
		return this;
	}

	@Override
	public void attach() {
		super.attach();
		for (WidgetI w : this.getChildWidgetList()) {
			w.attach();
		}
	}

	@Override
	public void detach() {
		for (WidgetI w : this.getChildWidgetList()) {
			w.detach();
		}
		super.detach();
	}

	@Override
	public List<WidgetI> getChildWidgetList() {
		return this.getChildList(WidgetI.class);
	}

}
