/**
 *  Jan 15, 2013
 */
package com.graphscape.gwt.core.html5;

import com.google.gwt.core.client.JavaScriptObject;
import com.graphscape.gwt.core.html5.AbstractJSO;
import com.graphscape.gwt.core.html5.DefaultJSO;

/**
 * @author wuzhen
 * 
 */
public final class ErrorJSO extends AbstractJSO {

	protected ErrorJSO() {

	}

	public final DefaultJSO getData() {
		Object obj = this.getProperty("data");
		if (obj == null) {
			return null;
		}
		JavaScriptObject js = (JavaScriptObject) obj;
		DefaultJSO rt = js.cast();

		return rt;

	}
}
