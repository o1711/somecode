/**
 * Jul 1, 2012
 */
package com.graphscape.gwt.commons.provider.default_.widget.table;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.graphscape.gwt.commons.provider.default_.widget.table.RowImpl;
import com.graphscape.gwt.commons.provider.default_.widget.table.support.TableHelper;
import com.graphscape.gwt.commons.widget.table.TableI;
import com.graphscape.gwt.commons.widget.table.TableI.CellI;
import com.graphscape.gwt.commons.widget.table.TableI.RowI;
import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.core.WidgetI;

/**
 * @author wu
 * 
 */
public class CellImpl extends TableHelper implements TableI.CellI {

	protected RowI row;

	/** */
	public CellImpl(ContainerI c, RowImpl r) {
		super(c, DOM.createTD(), r.getTable());
		this.row = r;
	}

	/* */
	@Override
	public CellI child(WidgetI w) {
		super.child(w);
		return this;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.commons.uicommons.api.gwt.client.widget.table.TableI.CellI#colspan(int)
	 */
	@Override
	public CellI colspan(int cs) {
		Element ele = this.element;
		setAttribute(ele, "colspan", cs);
		return this;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.commons.uicommons.api.gwt.client.widget.table.TableI.CellI#rowspan(int)
	 */
	@Override
	public CellI rowspan(int rs) {
		//this.setAttribuate not support non-string type,and will throw a TypeError.
		Element ele = this.element;
		setAttribute(ele, "rowspan", rs);
		return this;
	}

	public static final native void setAttribute(Element ele, String name, Object value)
	/*-{
		ele.setAttribute(name, value);
	}-*/;

}
