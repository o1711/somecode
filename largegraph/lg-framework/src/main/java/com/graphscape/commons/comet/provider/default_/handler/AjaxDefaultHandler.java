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
public class AjaxDefaultHandler extends AjaxMsgHandler {

	/**
	 * @param sessionManager
	 * @param manager
	 */

	public AjaxDefaultHandler(SessionManagerI sm, AjaxCometManagerImpl manager) {
		super(true, sm, manager);
	}

	/*
	 * May 8, 2013
	 */
	@Override
	public void handlerInternal(AjaxMsgContext amc) {

		amc.arc.writeError("no-handler", "for path:" + amc.am.getPath().toString());

	}
}
