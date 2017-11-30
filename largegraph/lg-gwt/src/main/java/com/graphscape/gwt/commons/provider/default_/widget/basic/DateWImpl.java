/**
 * Jun 29, 2012
 */
package com.graphscape.gwt.commons.provider.default_.widget.basic;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.graphscape.gwt.commons.widget.basic.DateWI;
import com.graphscape.gwt.commons.widget.support.WidgetSupport;
import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.data.basic.DateData;
import com.graphscape.gwt.core.util.DateUtil;

/**
 * @author wu
 * 
 */
public class DateWImpl extends WidgetSupport implements DateWI {

	/** */
	public DateWImpl(ContainerI c, String name) {
		super(c, name, DOM.createLabel());
	}

	@Override
	public void setDate(DateData date) {

		Element ele = this.getElement();

		DateData sd = date;
		String str = DateUtil.format(sd, false);
		ele.setInnerText(str);//

		ele.setTitle(str);

	}
}
