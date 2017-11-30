/**
 * Jul 1, 2012
 */
package com.graphscape.gwt.commons.provider.default_.widget.table.support;

import com.google.gwt.user.client.Element;
import com.graphscape.gwt.commons.provider.default_.widget.table.TableImpl;
import com.graphscape.gwt.commons.widget.support.LayoutSupport;
import com.graphscape.gwt.core.ContainerI;

/**
 * @author wu
 * 
 */
public class TableHelper extends LayoutSupport {

	protected TableImpl table;

	public TableHelper(ContainerI c, Element ele, TableImpl t) {
		super(c, ele);
		this.table = t;
	}

	/**
	 * @return the table
	 */
	public TableImpl getTable() {
		return table;
	}
}
