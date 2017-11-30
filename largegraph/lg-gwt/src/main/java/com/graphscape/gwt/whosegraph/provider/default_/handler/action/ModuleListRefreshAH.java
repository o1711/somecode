package com.graphscape.gwt.whosegraph.provider.default_.handler.action;

import com.graphscape.gwt.commons.event.ActionEvent;
import com.graphscape.gwt.commons.frwk.login.LoginControlI;
import com.graphscape.gwt.commons.frwk.password.PasswordResetViewI;
import com.graphscape.gwt.commons.handler.ActionHandlerSupport;
import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.MsgWrapper;
import com.graphscape.gwt.core.commons.Path;
/**
 * 
 * @author wuzhen
 *
 */
public class ModuleListRefreshAH extends ActionHandlerSupport {

	public ModuleListRefreshAH(ContainerI c) {
		super(c);
	}

	@Override
	public void handle(ActionEvent t) {
		
		MsgWrapper req = this.newRequest(Path.valueOf("module-list/refresh"));
		
		this.sendMessage(req);
	}

}
