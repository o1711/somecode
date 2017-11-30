/**
 * Jun 25, 2012
 */
package com.graphscape.gwt.commons.widget;

import com.graphscape.gwt.core.core.WidgetI;

/**
 * @author wuzhen
 * 
 */
public interface EditorI<T> extends WidgetI {

	public T getData();
	
	public void setData(T d);

	public void input(T d);

}
