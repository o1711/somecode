/**
 * All right is from Author of the file,to be explained in comming days.
 * May 8, 2013
 */
package com.fs.webcomet.impl.ajax.handlers;

import com.fs.commons.api.session.SessionManagerI;
import com.fs.webcomet.impl.ajax.AjaxCometManagerImpl;
import com.fs.webcomet.impl.ajax.AjaxMsg;
import com.fs.webcomet.impl.ajax.AjaxMsgContext;
import com.fs.webcomet.impl.ajax.AjaxMsgHandler;

/**
 * @author wu
 * 
 */
public class AjaxMessageHandler extends AjaxMsgHandler {

	/**
	 * @param sessionMap
	 * @param manager
	 */

	public AjaxMessageHandler(SessionManagerI sessionMap, AjaxCometManagerImpl manager) {
		super(true, sessionMap, manager);
	}

	/*
	 * May 8, 2013
	 */
	@Override
	public void handlerInternal(AjaxMsgContext amc) {
		String msg = (String) amc.am.getProperty(AjaxMsg.PK_TEXTMESSAGE, true);
		this.manager.onMessage(amc.arc.as, msg);

	}
}
