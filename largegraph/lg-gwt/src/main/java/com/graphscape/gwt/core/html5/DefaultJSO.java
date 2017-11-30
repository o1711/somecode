/**
 *  Jan 16, 2013
 */
package com.graphscape.gwt.core.html5;

import com.graphscape.gwt.core.html5.AbstractJSO;
import com.graphscape.gwt.core.html5.DefaultJSO;

/**
 * @author wuzhen
 * 
 */
public final class DefaultJSO extends AbstractJSO {

	protected DefaultJSO() {

	}

	public static native final DefaultJSO newInstance()
	/*-{
		return {};
	}-*/;

}
