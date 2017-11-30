/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 10, 2012
 */
package com.graphscape.gwt.test.core.widget;

import com.google.gwt.user.client.DOM;
import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.support.WidgetBase;

/**
 * @author wu
 * 
 */
public class TestWidget extends WidgetBase implements TestWI {

	/**
	 * @param ele
	 */
	public TestWidget(ContainerI c, String name) {
		super(c, name, DOM.createDiv());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.commons.uicore.api.gwt.client.support.WidgetBase#doUpdate(com.fs.uicore
	 * .api.gwt.client.ModelI)
	 */
	@Override
	public void setText(String txt) {

		this.element.setInnerText(txt);
	}

}
