/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 24, 2012
 */
package com.graphscape.gwt.commons.provider.default_.dom;

import com.google.gwt.user.client.DOM;
import com.graphscape.gwt.commons.provider.default_.dom.TDWrapper;
import com.graphscape.gwt.core.dom.ElementWrapper;

/**
 * @author wu
 * 
 */
public class TRWrapper extends ElementWrapper {

	/**
	 * @param ele
	 */
	public TRWrapper() {
		super(DOM.createTR());
	}

	public TDWrapper addTd() {
		TDWrapper rt = new TDWrapper();
		this.append(rt);
		return rt;
	}
}
