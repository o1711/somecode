package com.graphscape.gwt.core.model;

import com.graphscape.gwt.core.ModelI;
import com.graphscape.gwt.core.support.ModelSupport;

public class ProxyModel extends ModelSupport implements ModelI {

	protected ModelI target;

	public ProxyModel(ModelI target) {
		super("proxy-" + target.getName());

		this.target = target;
	}

}
