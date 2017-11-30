/**
 *  
 */
package com.graphscape.gwt.commons.provider.default_.widget.html;

import com.google.gwt.user.client.DOM;
import com.graphscape.gwt.commons.widget.html.HtmlElementWidgetI;
import com.graphscape.gwt.commons.widget.support.WidgetSupport;
import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.commons.UiPropertiesI;

/**
 * @author wu
 * 
 */
public class HtmlElementWidgetImpl extends WidgetSupport implements HtmlElementWidgetI {

	/**
	 * @param c
	 * @param name
	 * @param ele
	 */
	public HtmlElementWidgetImpl(ContainerI c, String name, UiPropertiesI<Object> pts) {
		// TODO element type as parameter?
		super(c, name, DOM.createDiv(), pts);
	}

	@Override
	public void setInnerHtml(String html) {
		this.element.setInnerHTML(html);
	}

}
