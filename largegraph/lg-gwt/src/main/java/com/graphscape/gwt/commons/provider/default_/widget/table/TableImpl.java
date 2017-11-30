/**
 * Jul 1, 2012
 */
package com.graphscape.gwt.commons.provider.default_.widget.table;

import com.google.gwt.user.client.DOM;
import com.graphscape.gwt.commons.provider.default_.widget.table.BodyImpl;
import com.graphscape.gwt.commons.provider.default_.widget.table.HeadersImpl;
import com.graphscape.gwt.commons.widget.support.LayoutSupport;
import com.graphscape.gwt.commons.widget.table.TableI;
import com.graphscape.gwt.core.ContainerI;

/**
 * @author wu
 * 
 */
public class TableImpl extends LayoutSupport implements TableI {

	protected HeadersI headers;

	protected BodyI body;

	/** */
	public TableImpl(ContainerI c, String name) {
		super(c,name, DOM.createTable());
		this.element.setAttribute("cellspacing", "0");
		
		this.element.setAttribute("cellspading", "0");
		
		this.headers = new HeadersImpl(this.container,this);
		this.child(this.headers);
		this.body = new BodyImpl(this.container,this);
		this.child(this.body);//
	}


	/* */
	@Override
	public HeadersI getHeaders() {

		return this.headers;

	}

	/* */
	@Override
	public BodyI getBody() {

		return this.body;

	}

}
