/**
 * Jun 25, 2012
 */
package com.graphscape.gwt.commons.mvc.support;

import com.google.gwt.user.client.Element;
import com.graphscape.gwt.commons.event.ActionEvent;
import com.graphscape.gwt.commons.mvc.ControlI;
import com.graphscape.gwt.commons.mvc.ControlManagerI;
import com.graphscape.gwt.commons.mvc.ViewI;
import com.graphscape.gwt.commons.widget.support.LayoutSupport;
import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.MsgWrapper;
import com.graphscape.gwt.core.UiException;
import com.graphscape.gwt.core.commons.Path;
import com.graphscape.gwt.core.data.ErrorInfosData;
import com.graphscape.gwt.core.data.message.MessageData;
import com.graphscape.gwt.core.endpoint.EndPointI;
import com.graphscape.gwt.core.logger.UiLoggerFactory;
import com.graphscape.gwt.core.logger.UiLoggerI;

/**
 * @author wuzhen
 * 
 */
public class ViewSupport extends LayoutSupport implements ViewI {
	private static final UiLoggerI LOG = UiLoggerFactory.getLogger(ViewSupport.class);

	public ViewSupport(ContainerI c, Element ele) {
		this(c, null, ele);
	}

	public ViewSupport(ContainerI c, String name, Element ele) {
		super(c, name, ele);
	}

	@Override
	public void clickAction(Path a) {
		throw new UiException("TODO");
	}

	/**
	 * @param name
	 */
	protected void dispatchActionEvent(Path name) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("dispatching action event,path:" + name);
		}
		ActionEvent ae = this.newActionEvent(name);
		this.beforeActionEvent(ae);
		ae.dispatch();
	}

	//
	protected ActionEvent newActionEvent(Path aname) {
		ActionEvent rt = new ActionEvent(this, (aname));

		return rt;
	}

	protected void beforeActionEvent(ActionEvent ae) {

	}

	protected EndPointI getEndpoint() {
		return this.getClient(true).getEndpoint(true);
	}

	protected void sendMessage(MsgWrapper req) {
		this.getEndpoint().sendMessage(req);
	}

	protected void sendMessage(MessageData req) {
		this.getEndpoint().sendMessage(req);
	}

	protected <T extends ControlI> T getControl(Class<T> cls, boolean force) {
		return this.getControlManager().getControl(cls, force);
	}

	protected ControlManagerI getControlManager() {

		return this.getClient(true).getChild(ControlManagerI.class, true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.commons.uicommons.api.gwt.client.mvc.ViewI#addErrorInfo(com.fs.commons.uicore.
	 * api.gwt.client.data.ErrorInfosData)
	 */
	@Override
	public void addErrorInfo(ErrorInfosData eis) {

	}

	@Override
	public void clearErrorInfo() {

	}
}
