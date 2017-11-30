/**
 * All right is from Author of the file,to be explained in comming days.
 * May 8, 2013
 */
package org.cellang.commons.transfer.ajax;

import org.cellang.commons.session.SessionManager;
import org.cellang.commons.transfer.DefaultCometManager;

/**
 * @author wu
 * 
 */
public class AjaxHeartBeatHandler extends AjaxMsgHandler {

	/**
	 * @param sessionMap
	 * @param manager
	 */

	public AjaxHeartBeatHandler(SessionManager sessionMap, DefaultCometManager manager) {
		super(true, sessionMap, manager);
	}

	/*
	 * May 8, 2013
	 */
	@Override
	public void handlerInternal(AjaxMsgContext amc) {
		// do nothing.
	}
}
