/**
 *  Jan 31, 2013
 */
package com.graphscape.gwt.commons.frwk;

import com.graphscape.gwt.commons.frwk.BodyViewI;
import com.graphscape.gwt.commons.frwk.BottomViewI;
import com.graphscape.gwt.commons.frwk.HeaderViewI;
import com.graphscape.gwt.commons.mvc.ViewI;

/**
 * @author wuzhen
 * 
 */
public interface FrwkViewI extends ViewI {

	public BodyViewI getBodyView();

	public HeaderViewI getHeader();
	
	public BottomViewI getBottom();

}
