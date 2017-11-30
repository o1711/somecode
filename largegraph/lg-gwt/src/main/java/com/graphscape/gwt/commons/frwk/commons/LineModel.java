package com.graphscape.gwt.commons.frwk.commons;

import com.graphscape.gwt.core.ModelI;
import com.graphscape.gwt.core.support.ModelSupport;

public class LineModel extends ModelSupport {

	public static final Location L_TEXT = ModelI.L_DEFAULT;

	public LineModel(String name) {
		super(name);
	}

	public String getText() {
		return (String) this.getValue(L_TEXT);
	}

}
