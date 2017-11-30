/**
 *  Jan 15, 2013
 */
package com.graphscape.gwt.core.html5;

import com.graphscape.gwt.core.html5.AbstractJSO;

/**
 * @author wuzhen
 * 
 */
public final class EventJSO extends AbstractJSO {

	protected EventJSO() {

	}

	public native String getData()
	/*-{
		return this.data;
	}-*/;
}
