/**
 * All right is from Author of the file.
 * Any usage of the code must be authorized by the the auther.
 * If not sure the detail of the license,please distroy the copies immediately.  
 * Nov 15, 2012
 */
package com.graphscape.gwt.commons.widget.support;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.graphscape.gwt.commons.AdjusterI;
import com.graphscape.gwt.commons.widget.support.AdjusterImpl;
import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.core.WidgetI;
import com.graphscape.gwt.core.core.Event.EventHandlerI;
import com.graphscape.gwt.core.event.ClickEvent;
import com.graphscape.gwt.core.support.ElementObjectSupport;

/**
 * @author wuzhen
 *         <p>
 *         Note: Adjuster is not a WidgetI
 *         <p>
 *         TODO extends a UiElementSupport,add a common interface: UiElementI
 */

public class AdjusterImpl extends ElementObjectSupport implements AdjusterI {

	public AdjusterImpl(ContainerI c, String name) {
		super(c, name, DOM.createDiv());
		this.element.setInnerText(name);// TODO i18n/image
		this.element.setTitle(name);// TODO title
		this.element.addClassName("adjuster");
		this.addGwtHandler(com.google.gwt.event.dom.client.ClickEvent.getType(), new ClickHandler() {

			@Override
			public void onClick(com.google.gwt.event.dom.client.ClickEvent event) {
				AdjusterImpl.this.onClick();
			}
		});
	}

	protected void onClick() {
		new ClickEvent(this).dispatch();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.commons.uicore.api.gwt.client.core.AdjusterI#addClickHandler(com.fs.uicore
	 * .api.gwt.client.core.Event.HandlerI)
	 */
	@Override
	public void addClickHandler(EventHandlerI<ClickEvent> eh) {
		this.addHandler(ClickEvent.TYPE, eh);//

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.commons.uicore.api.gwt.client.core.AdjusterI#getOwner()
	 */
	@Override
	public WidgetI getOwner() {
		// TODO Auto-generated method stub
		return (WidgetI) this.parent;
	}

}
