/**
 *  Jan 31, 2013
 */
package com.fs.uicommons.api.gwt.client.frwk;

import com.fs.uicommons.api.gwt.client.mvc.ViewI;

/**
 * @author wuzhen
 * 
 */
public interface FrwkViewI extends ViewI {

	public BodyViewI getBodyView();

	public HeaderViewI getHeader();
	
	public BottomViewI getBottom();

}
