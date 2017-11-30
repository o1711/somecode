/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 3, 2012
 */
package com.graphscape.gwt.commons.provider.default_.widget.menu;

import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.graphscape.gwt.commons.widget.menu.MenuItemWI;
import com.graphscape.gwt.commons.widget.support.WidgetSupport;
import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.event.ClickEvent;

/**
 * @author wu
 * 
 */
public class MenuItemWImpl extends WidgetSupport implements MenuItemWI {

	/**
	 * @param ele
	 */
	public MenuItemWImpl(ContainerI c, String name) {
		super(c, name, DOM.createAnchor());

		this.addGwtHandler(com.google.gwt.event.dom.client.ClickEvent.getType(), new ClickHandler() {

			@Override
			public void onClick(com.google.gwt.event.dom.client.ClickEvent event) {
				MenuItemWImpl.this.onGwtClick(event);
			}//
		});
	}

	public void onGwtClick(com.google.gwt.event.dom.client.ClickEvent event) {
		new ClickEvent(this).dispatch();
	}

	@Override
	public void setText(boolean toloc, String text) {
		if (toloc) {
			text = this.getClient(true).localized(text);//
		}

		AnchorElement ele = this.concreteElement();

		ele.setHref("#");
		ele.setInnerText(text);
		ele.setTitle(text);

	}

	protected AnchorElement concreteElement() {
		AnchorElement ae = this.getElement().cast();
		return ae;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.commons.uicommons.api.gwt.client.widget.menu.MenuItemWI#_click()
	 */
	@Override
	public void _click() {
		AnchorElement ae = this.getElement().cast();
		// this.archorClick(ae);//TODO
		// see com.google.gwt.user.client.ui.CustomButton
		NativeEvent evt = Document.get().createClickEvent(1, 0, 0, 0, 0, false, false, false, false);
		ae.dispatchEvent(evt);
	}

	//
	/**
	 * TODO this method not work.
	 * 
	 * 
	 * It's said that this should works in firefox,see: <code> 
	 http://stackoverflow.com/questions/773639/how-can-i-simulate-an-anchor-click-via-jquery
 	var comp = document.getElementById('yourCompId');
try { //in firefox
    comp.click();
    return;
} catch(ex) {}
try { // in chrome
    if(document.createEvent) {
        var e = document.createEvent('MouseEvents');
        e.initEvent( 'click', true, true );
        comp.dispatchEvent(e);
        return;
    }
} catch(ex) {}
try { // in IE
    if(document.createEventObject) {
         var evObj = document.createEventObject();
         comp.fireEvent("onclick", evObj);
         return;
    }
} catch(ex) {}
</code>
	 * 
	 * @param anchor
	 */
	public native void archorClick(AnchorElement anchor) /*-{
															anchor.click();
															}-*/;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.commons.uicommons.api.gwt.client.widget.menu.MenuItemWI#_select()
	 */
	@Override
	public void _select() {
		// TODO
	}

}
