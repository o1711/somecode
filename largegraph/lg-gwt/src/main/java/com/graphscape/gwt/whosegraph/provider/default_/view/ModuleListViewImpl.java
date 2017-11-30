/**
 * 
 */
package com.graphscape.gwt.whosegraph.provider.default_.view;

import com.google.gwt.user.client.DOM;
import com.graphscape.gwt.commons.mvc.support.ViewSupport;
import com.graphscape.gwt.commons.widget.list.ListI;
import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.whosegraph.data.ModuleData;
import com.graphscape.gwt.whosegraph.view.ModuleListViewI;

/**
 * 
 * @author wuzhen
 * 
 */
public class ModuleListViewImpl extends ViewSupport implements ModuleListViewI {

	protected ListI list;

	public ModuleListViewImpl(ContainerI c) {
		super(c, DOM.createDiv());
		this.list = this.factory.create(ListI.class);
		this.list.parent(this);
	}

	@Override
	public void addModule(ModuleData md) {

	}

}
