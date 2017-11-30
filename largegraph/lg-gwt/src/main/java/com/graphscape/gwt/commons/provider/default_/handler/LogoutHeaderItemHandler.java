/**
 *  Jan 31, 2013
 */
package com.graphscape.gwt.commons.provider.default_.handler;

import com.graphscape.gwt.commons.event.HeaderItemEvent;
import com.graphscape.gwt.commons.frwk.FrwkControlI;
import com.graphscape.gwt.commons.frwk.HeaderViewI;
import com.graphscape.gwt.commons.mvc.support.UiHandlerSupport;
import com.graphscape.gwt.commons.provider.default_.frwk.login.AccountsLDW;
import com.graphscape.gwt.commons.provider.default_.frwk.login.RegisteredAccountLDW;
import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.core.Event.EventHandlerI;
import com.graphscape.gwt.core.endpoint.EndPointI;
import com.graphscape.gwt.core.endpoint.UserInfo;

/**
 * @author wuzhen
 * 
 */
public class LogoutHeaderItemHandler extends UiHandlerSupport implements EventHandlerI<HeaderItemEvent> {

	/**
	 * @param c
	 */
	public LogoutHeaderItemHandler(ContainerI c) {
		super(c);
	}

	@Override
	public void handle(HeaderItemEvent t) {
		EndPointI e = this.getEndpoint();
		UserInfo ui = e.getUserInfo();
		if (!ui.isAnonymous()) {
			//invalid the saved info for registered user 
			RegisteredAccountLDW ral = AccountsLDW.getInstance().getRegistered();
			if (ral.isValid()) {
				ral.invalid();
			}
		}
		FrwkControlI fc = this.getControl(FrwkControlI.class, true);
		HeaderViewI hv = fc.getHeaderView();
		// just close the connect to server.
		e.close();//
	}

}
