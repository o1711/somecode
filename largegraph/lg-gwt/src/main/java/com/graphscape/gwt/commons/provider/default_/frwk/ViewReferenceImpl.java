/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 15, 2012
 */
package com.graphscape.gwt.commons.provider.default_.frwk;

import com.graphscape.gwt.commons.frwk.ViewReferenceI;
import com.graphscape.gwt.commons.provider.default_.frwk.BodyModel;
import com.graphscape.gwt.core.commons.Path;
import com.graphscape.gwt.core.core.WidgetI;
import com.graphscape.gwt.core.support.ModelSupport;

/**
 * @author wu
 * 
 */
public class ViewReferenceImpl implements ViewReferenceI {

	private WidgetI managedWidget;

	private boolean selected;

	private BodyModel body;

	private Path path;

	/**
	 * @param name
	 */
	public ViewReferenceImpl(Path path, WidgetI mw, BodyModel bm) {
		this.path = path;
		this.managedWidget = mw;
		this.body = bm;
	}

	@Override
	public void select(boolean sel) {
		//
		this.selected = sel;
		this.body.selectView(this, sel);
		// TODO
	}

	@Override
	public boolean isSelect() {
		return this.selected;
	}

	@Override
	public WidgetI getManagedWidget() {
		return this.managedWidget;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.commons.uicommons.api.gwt.client.frwk.ViewReferenceI#getPath()
	 */
	@Override
	public Path getPath() {
		return this.path;
	}

}
