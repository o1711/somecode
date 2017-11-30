/**
 * All right is from Author of the file,to be explained in comming days.
 * May 8, 2013
 */
package com.graphscape.commons.comet.provider.default_;

import com.graphscape.commons.session.SessionManagerI;

/**
 * @author wu
 * 
 */
public abstract class AjaxMsgHandler {
	protected SessionManagerI sessionManager;
	protected AjaxCometManagerImpl manager;
	protected boolean sessionRequired;

	/**
	 * @return the sessionRequired
	 */
	public boolean isSessionRequired() {
		return sessionRequired;
	}

	public AjaxMsgHandler(boolean sr, SessionManagerI sessionMap,
			AjaxCometManagerImpl manager) {

		this.sessionManager = sessionMap;
		this.manager = manager;
		this.sessionRequired = sr;
	}

	public void handle(AjaxMsgContext amc) {
		if (this.sessionRequired && amc.arc.as == null) {
			amc.arc.writeError("bad-request",
					"session id is missing, please set http header:"
							+ AjaxCometHttpRequestHandler.HK_SESSION_ID);
		}

		this.handlerInternal(amc);

	}

	public abstract void handlerInternal(AjaxMsgContext amc);
}
