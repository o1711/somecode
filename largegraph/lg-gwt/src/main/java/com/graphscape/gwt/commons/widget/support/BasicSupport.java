/**
 * Jun 30, 2012
 */
package com.graphscape.gwt.commons.widget.support;

import com.google.gwt.user.client.Element;
import com.graphscape.gwt.commons.widget.support.WidgetSupport;
import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.UiException;
import com.graphscape.gwt.core.core.UiObjectI;

/**
 * @author wu
 * 
 */
public class BasicSupport extends WidgetSupport {

	/** */
	public BasicSupport(ContainerI c, String name, Element ele) {
		super(c, name, ele);

	}

	/* */
	@Override
	public void addChild(UiObjectI c) {
		throw new UiException("only compisite can add child.");
	}

}
