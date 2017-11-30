/**
 * Jun 12, 2012
 */
package com.graphscape.gwt.commons.mvc;

import com.graphscape.gwt.core.commons.Path;
import com.graphscape.gwt.core.core.WidgetI;
import com.graphscape.gwt.core.data.ErrorInfosData;

/**
 * @author wuzhen
 * 
 */
public interface ViewI extends WidgetI {

	public void clickAction(Path a);
	
	public void addErrorInfo(ErrorInfosData eis);
	
	public void clearErrorInfo();

}
