/**
 * 
 */
package com.graphscape.gwt.commons.widget.error;

import com.graphscape.gwt.core.core.WidgetI;
import com.graphscape.gwt.core.data.ErrorInfosData;

/**
 * @author wuzhen
 * 
 */
public interface ErrorInfosWidgetI extends WidgetI {

	public void addErrorInfos(ErrorInfosData errorInfos);

	public void clear();

}
