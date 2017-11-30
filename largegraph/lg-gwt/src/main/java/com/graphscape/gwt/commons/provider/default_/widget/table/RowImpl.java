/**
 * Jul 1, 2012
 */
package com.graphscape.gwt.commons.provider.default_.widget.table;

import com.google.gwt.user.client.DOM;
import com.graphscape.gwt.commons.provider.default_.widget.table.BodyImpl;
import com.graphscape.gwt.commons.provider.default_.widget.table.CellImpl;
import com.graphscape.gwt.commons.provider.default_.widget.table.support.TableHelper;
import com.graphscape.gwt.commons.widget.table.TableI;
import com.graphscape.gwt.commons.widget.table.TableI.CellI;
import com.graphscape.gwt.core.ContainerI;

/**
 * @author wu
 * 
 */
public class RowImpl extends TableHelper implements TableI.RowI {

	protected BodyImpl body;

	/** */
	public RowImpl(ContainerI c, BodyImpl t) {
		super(c,DOM.createTR(), t.getTable());
		this.body = t;
	}

	/* */
	@Override
	public CellI createCell() {
		CellI rt = new CellImpl(this.container, this);
		this.child(rt);
		return rt;

	}

}
