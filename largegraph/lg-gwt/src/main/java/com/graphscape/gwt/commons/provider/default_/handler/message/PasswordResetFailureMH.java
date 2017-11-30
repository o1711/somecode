/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 2, 2013
 */
package com.graphscape.gwt.commons.provider.default_.handler.message;

import com.google.gwt.user.client.Window;
import com.graphscape.gwt.commons.frwk.login.LoginControlI;
import com.graphscape.gwt.commons.frwk.login.LoginViewI;
import com.graphscape.gwt.commons.frwk.password.PasswordResetViewI;
import com.graphscape.gwt.commons.mvc.support.UiHandlerSupport;
import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.UiResponse;
import com.graphscape.gwt.core.data.ErrorInfosData;
import com.graphscape.gwt.core.data.message.MessageData;
import com.graphscape.gwt.core.event.EndpointMessageEvent;
import com.graphscape.gwt.core.message.MessageHandlerI;

/**
 * @author wu
 * 
 */
public class PasswordResetFailureMH extends UiHandlerSupport implements MessageHandlerI<EndpointMessageEvent> {

	/**
	 * @param c
	 */
	public PasswordResetFailureMH(ContainerI c) {
		super(c);
	}

	/*
	 * Jan 2, 2013
	 */
	@Override
	public void handle(EndpointMessageEvent t) {
		MessageData req = t.getMessage();
		ErrorInfosData eis = (ErrorInfosData) req.getPayload(UiResponse.ERROR_INFO_S);
		LoginControlI lc = this.getControl(LoginControlI.class, true);
		PasswordResetViewI lm = lc.openPasswordResetView();
		lm.addErrorInfo(eis);//

	}

}
