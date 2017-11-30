/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 11, 2012
 */
package com.graphscape.gwt.commons.handler;

import com.graphscape.gwt.commons.event.ActionEvent;
import com.graphscape.gwt.commons.mvc.ViewI;
import com.graphscape.gwt.commons.mvc.support.UiHandlerSupport;
import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.core.Event.EventHandlerI;

/**
 * @author wu
 * 
 */
public abstract class ActionHandlerSupport extends UiHandlerSupport implements EventHandlerI<ActionEvent> {

	public ActionHandlerSupport(ContainerI c) {
		super(c);
	}

	protected <T extends ViewI> T findView(Class<T> vcls, boolean force) {
		return this.getClient(true).getRoot().find(vcls, force);
	}
}
