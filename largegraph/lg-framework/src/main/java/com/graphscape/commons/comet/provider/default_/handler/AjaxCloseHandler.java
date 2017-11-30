/**
 * All right is from Author of the file,to be explained in comming days.
 * May 8, 2013
 */
package com.graphscape.commons.comet.provider.default_.handler;

import com.graphscape.commons.comet.provider.default_.AjaxCometManagerImpl;
import com.graphscape.commons.comet.provider.default_.AjaxMsgContext;
import com.graphscape.commons.comet.provider.default_.AjaxMsgHandler;
import com.graphscape.commons.session.SessionManagerI;

/**
 * @author wu
 * 
 */
public class AjaxCloseHandler extends AjaxMsgHandler {

	/**
	 * @param sessionManager
	 * @param manager
	 */

	public AjaxCloseHandler(SessionManagerI sessionMap, AjaxCometManagerImpl manager) {
		super(true, sessionMap, manager);
	}

	/*
	 * May 8, 2013
	 */
	@Override
	public void handlerInternal(AjaxMsgContext amc) {
		amc.arc.writeCloseSuccess();
		if (amc.arc.as == null) {
			// ignore the session already closed.
			return;
		}
		//TODO remove the session from manager.
		//this.sessionMap.
		// fetch?
		// this.fetchMessage(amc);
		this.manager.onClose(amc.arc.as, 0, "");

	}
}
