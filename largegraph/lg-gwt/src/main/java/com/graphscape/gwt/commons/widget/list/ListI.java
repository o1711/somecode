/**
 * Jun 13, 2012
 */
package com.graphscape.gwt.commons.widget.list;

import com.graphscape.gwt.core.core.WidgetI;

/**
 * @author wu
 * 
 */
public interface ListI extends WidgetI {
	
	public static final String PK_IS_VERTICAL = "isVertical";
	
	public static final String PK_COMPARATOR = "comparator";
	
	public static final String PK_LIST_ITEM_CLASSNAME = "itemClassName"; 

	public int getSize();
}
