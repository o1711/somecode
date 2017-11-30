/**
 * Jun 29, 2012
 */
package com.graphscape.gwt.commons.provider.default_.widget.basic;

import com.google.gwt.user.client.DOM;
import com.graphscape.gwt.commons.widget.basic.ImageI;
import com.graphscape.gwt.commons.widget.support.WidgetSupport;
import com.graphscape.gwt.core.ContainerI;

/**
 * @author wu
 * 
 */
public class ImageImpl extends WidgetSupport implements ImageI {

	/** */
	public ImageImpl(ContainerI c, String name) {
		super(c, name, DOM.createImg());
	}

	@Override
	public void setSrc(String src) {
		this.element.setAttribute("src", src);
	}

}
