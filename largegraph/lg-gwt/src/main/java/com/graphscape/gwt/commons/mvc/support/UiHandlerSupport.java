/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 14, 2013
 */
package com.graphscape.gwt.commons.mvc.support;

import com.graphscape.gwt.commons.mvc.ControlI;
import com.graphscape.gwt.commons.mvc.ControlManagerI;
import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.MsgWrapper;
import com.graphscape.gwt.core.RootI;
import com.graphscape.gwt.core.ClientI;
import com.graphscape.gwt.core.commons.Path;
import com.graphscape.gwt.core.endpoint.EndPointI;

/**
 * @author wu
 * 
 */
public class UiHandlerSupport {

	protected ContainerI container;

	public UiHandlerSupport(ContainerI c) {
		this.container = c;
	}

	protected EndPointI getEndpoint() {
		return this.getClient(true).getEndpoint(true);
	}

	protected MsgWrapper newRequest(Path path) {
		return new MsgWrapper(path);
	}

	protected void sendMessage(MsgWrapper req) {
		this.getClient(true).getEndpoint(true).sendMessage(req);//
	}

	protected ControlManagerI getControlManager() {
		return this.getClient(true).getChild(ControlManagerI.class, true);
	}

	protected ClientI getClient(boolean force) {
		return this.container.get(ClientI.class, force);
	}

	protected RootI getRootView() {
		return this.getClient(true).getRoot();
	}

	protected <T extends ControlI> T getControl(Class<T> cls, boolean force) {
		return this.getControlManager().getControl(cls, force);
	}
}
