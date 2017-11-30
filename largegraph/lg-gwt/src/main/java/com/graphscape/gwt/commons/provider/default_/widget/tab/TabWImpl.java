/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 3, 2012
 */
package com.graphscape.gwt.commons.provider.default_.widget.tab;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.graphscape.gwt.commons.frwk.ViewReferenceI;
import com.graphscape.gwt.commons.provider.default_.widget.tab.TabWImpl;
import com.graphscape.gwt.commons.provider.default_.widget.tab.TabberWImpl;
import com.graphscape.gwt.commons.widget.event.SelectEvent;
import com.graphscape.gwt.commons.widget.panel.PanelWI;
import com.graphscape.gwt.commons.widget.stack.StackItemI;
import com.graphscape.gwt.commons.widget.support.WidgetSupport;
import com.graphscape.gwt.commons.widget.tab.TabWI;
import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.core.WidgetI;
import com.graphscape.gwt.core.core.Event.EventHandlerI;

/**
 * @author wu
 * 
 */
public class TabWImpl extends WidgetSupport implements TabWI {

	protected TabberWImpl tabber;

	protected PanelWI panel;

	protected StackItemI stackItem;

	protected boolean selected;
	protected WidgetI managed;

	/**
	 * @param ele
	 */
	public TabWImpl(ContainerI c, String name, PanelWI panel,WidgetI managed, StackItemI sitem, TabberWImpl tabber) {
		super(c, name, DOM.createDiv());
		this.panel = panel;
		this.managed = managed;
		this.tabber = tabber;
		this.stackItem = sitem;

		this.addGwtHandler(com.google.gwt.event.dom.client.ClickEvent.getType(), new ClickHandler() {

			@Override
			public void onClick(com.google.gwt.event.dom.client.ClickEvent event) {
				TabWImpl.this.onGwtClick(event);
			}//
		});
		
		this.setText(true, name);

	}

	private void onGwtClick(com.google.gwt.event.dom.client.ClickEvent event) {
		this.select();
	}

	// this not impact the tabber,only impact this widget itself
	// please call select,it will connected with tabber.
	public void setSelected(boolean sel, boolean dis) {
		this.selected = sel;

		this.getElementWrapper().addAndRemoveClassName(sel, "position-selected", "position-unselected");
		if (dis) {
			new SelectEvent(this, sel).dispatch();//
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.commons.uicommons.api.gwt.client.widget.tab.TabWI#show()
	 */
	@Override
	public void select() {

		this.tabber._select(this.name);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.commons.uicommons.api.gwt.client.widget.tab.TabWI#getPanel()
	 */
	@Override
	public PanelWI getPanel() {

		return this.panel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.commons.uicommons.api.gwt.client.widget.tab.TabWI#getName()
	 */
	@Override
	public String getName() {

		return this.name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.commons.uicore.api.gwt.client.support.WidgetBase#doUpdate(com.fs.uicore
	 * .api.gwt.client.core.ModelI)
	 */
	@Override
	public void setText(boolean i18n, String text) {
		
		if(i18n){
			text = this.getClient(true).localized(text);
		}
		
		if (this.isSelected()) {
			text += "*";// TODO other way to show the selected tab.
		}
		
		this.element.setInnerText(text);//

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.commons.uicommons.api.gwt.client.widget.tab.TabWI#isSelected()
	 */
	@Override
	public boolean isSelected() {
		return this.selected;
	}

	/**
	 * @return the stackItem
	 */
	public ViewReferenceI getStackItem() {
		return stackItem;
	}

	@Override
	public void addSelectEventHandler(EventHandlerI<SelectEvent> eh) {
		this.addHandler(SelectEvent.TYPE, eh);
	}

	/* (non-Javadoc)
	 * @see com.fs.commons.uicommons.api.gwt.client.widget.tab.TabWI#close()
	 */
	@Override
	public void close() {
		this.stackItem.remove();
	}

	/* (non-Javadoc)
	 * @see com.fs.commons.uicommons.api.gwt.client.widget.tab.TabWI#getManaged()
	 */
	@Override
	public WidgetI getManaged() {
		return this.managed;
	}

}
