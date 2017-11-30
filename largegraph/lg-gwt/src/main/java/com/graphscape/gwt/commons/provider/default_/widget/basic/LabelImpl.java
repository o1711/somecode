/**
 * Jun 29, 2012
 */
package com.graphscape.gwt.commons.provider.default_.widget.basic;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.graphscape.gwt.commons.widget.basic.LabelI;
import com.graphscape.gwt.commons.widget.support.WidgetSupport;
import com.graphscape.gwt.core.ContainerI;

/**
 * @author wu
 * 
 */
public class LabelImpl extends WidgetSupport implements LabelI {

	/** */
	public LabelImpl(ContainerI c, String name) {
		super(c, name, DOM.createLabel());
	}

	@Override
	public void setText(String sd) {
		this.setText(sd, false);
	}

	@Override
	public void setText(String sd, boolean loc) {
		//
		if (loc) {
			sd = this.localized(sd);
		}
		Element ele = this.getElement();

		ele.setInnerText(sd);//
	}

	/*
	 * Apr 13, 2013
	 */
	@Override
	public void setTitle(String title) {
		this.getElement().setTitle(title);
	}

	/*
	 * Apr 13, 2013
	 */
	@Override
	public void setTextAndTitle(String sd, boolean loc, String title) {
		this.setText(sd,loc);
		this.setTitle(title);
	}
}
