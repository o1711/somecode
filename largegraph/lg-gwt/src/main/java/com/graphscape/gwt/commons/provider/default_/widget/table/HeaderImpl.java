/**
 * Jul 1, 2012
 */
package com.graphscape.gwt.commons.provider.default_.widget.table;

import com.google.gwt.user.client.DOM;
import com.graphscape.gwt.commons.provider.default_.widget.table.TableImpl;
import com.graphscape.gwt.commons.provider.default_.widget.table.support.TableHelper;
import com.graphscape.gwt.commons.widget.table.TableI;
import com.graphscape.gwt.commons.widget.table.TableI.HeaderI;
import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.core.WidgetI;

/**
 * @author wu
 * 
 */
public class HeaderImpl extends TableHelper implements TableI.HeaderI {

	protected String name;

	/** */
	public HeaderImpl(ContainerI c, TableImpl t, String name) {
		super(c, DOM.createTH(), t);
		this.name = name;
	}

	/* */
	@Override
	public String getName() {

		return this.name;

	}

	/* */
	@Override
	public HeaderI child(WidgetI w) {
		super.child(w);
		return this;

	}

}
