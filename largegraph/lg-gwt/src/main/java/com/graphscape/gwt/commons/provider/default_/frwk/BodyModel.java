/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 13, 2012
 */
package com.graphscape.gwt.commons.provider.default_.frwk;

import java.util.HashMap;
import java.util.Map;

import com.graphscape.gwt.commons.event.BodyItemCreatedEvent;
import com.graphscape.gwt.commons.event.BodyItemSelectEvent;
import com.graphscape.gwt.commons.frwk.BodyModelI;
import com.graphscape.gwt.commons.frwk.ViewReferenceI;
import com.graphscape.gwt.commons.provider.default_.frwk.ViewReferenceImpl;
import com.graphscape.gwt.core.commons.Path;
import com.graphscape.gwt.core.core.WidgetI;
import com.graphscape.gwt.core.support.ModelSupport;

/**
 * @author wu
 * 
 */
public class BodyModel extends ModelSupport implements BodyModelI {

	protected Map<Path, ViewReferenceI> viewReferenceMap;

	/**
	 * @param name
	 */
	public BodyModel(String name) {
		super(name);
		this.viewReferenceMap = new HashMap<Path, ViewReferenceI>();
	}

	@Override
	public ViewReferenceI manage(Path p, WidgetI w) {
		ViewReferenceImpl rt = new ViewReferenceImpl(p, w, this);
		this.viewReferenceMap.put(p, rt);// TODO by a list?
		if (w instanceof ViewReferenceI.AwareI) {
			((ViewReferenceI.AwareI) w).setViewReference(rt);
		}
		new BodyItemCreatedEvent(this, p).dispatch();
		return rt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.commons.uicommons.api.gwt.client.frwk.CenterModelI#getManaged(java.lang
	 * .String)
	 */
	@Override
	public ViewReferenceI getManaged(Path name) {
		ViewReferenceI rt = this.viewReferenceMap.get(name);
		return rt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.commons.uicommons.api.gwt.client.frwk.CenterModelI#showManaged(java.lang
	 * .String)
	 */
	@Override
	public void showManaged(Path managed) {
		this.getManaged(managed).select(true);
	}

	public void selectView(ViewReferenceI vr, boolean sel) {
		new BodyItemSelectEvent(this, vr.getPath(), sel).dispatch();
	}

}
