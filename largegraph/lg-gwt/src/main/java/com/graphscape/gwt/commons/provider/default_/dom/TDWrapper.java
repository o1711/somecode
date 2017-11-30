/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 24, 2012
 */
package com.graphscape.gwt.commons.provider.default_.dom;

import com.google.gwt.user.client.DOM;
import com.graphscape.gwt.core.dom.ElementWrapper;

/**
 * @author wu
 * 
 */
public class TDWrapper extends ElementWrapper {

	/**
	 * @param ele
	 */
	public TDWrapper() {
		super(DOM.createTD());
	}

	public void setRowSpan(int rs) {
		this.setAttribute("rowspan", "" + rs);
	}
	
	public void setColspan(int cs){
		this.setAttribute("colspan", cs+"");
	}

}
