/**
 * Jul 1, 2012
 */
package com.graphscape.gwt.commons.provider.default_.widget.table;

import com.google.gwt.user.client.DOM;
import com.graphscape.gwt.commons.provider.default_.widget.table.support.TableHelper;
import com.graphscape.gwt.commons.widget.table.TableI;
import com.graphscape.gwt.commons.widget.table.TableI.RowI;
import com.graphscape.gwt.core.ContainerI;

/**
 * @author wu
 * 
 */
public class BodyImpl extends TableHelper implements TableI.BodyI {

	/** */
	public BodyImpl(ContainerI c, TableImpl t) {
		super(c, DOM.createTBody(), t);

	}

	/* */
	@Override
	public RowI createRow() {
		RowI rt = new RowImpl(this.container, this);
		this.child(rt);//
		return rt;

	}

}
