/**
 *  
 */
package com.graphscape.gwt.core.provider.default_.comet.ajax.handlers;

import com.graphscape.gwt.core.provider.default_.comet.ajax.AjaxGomet;
import com.graphscape.gwt.core.provider.default_.comet.ajax.ClientAjaxHandler;
import com.graphscape.gwt.core.provider.default_.comet.ajax.ClientAjaxMsgContext;


/**
 * @author wu
 *
 */
public class ConnectedHandler extends ClientAjaxHandler{

	/**
	 * @param client
	 */
	public ConnectedHandler(AjaxGomet client) {
		super(client);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.fs.commons.webcomet.impl.mock.ClientAjaxHandler#handle(com.fs.commons.webcomet.impl.mock.ClientAjaxMsgContext)
	 */
	@Override
	public void handle(ClientAjaxMsgContext amc) {
		String sid = amc.am.getSessionId(true);
		this.client.conected(sid);
		
	}

}
