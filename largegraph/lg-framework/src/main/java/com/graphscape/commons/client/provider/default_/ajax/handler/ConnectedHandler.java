/**
 *  
 */
package com.graphscape.commons.client.provider.default_.ajax.handler;

import com.graphscape.commons.client.provider.default_.ajax.AjaxMessageClient;
import com.graphscape.commons.client.provider.default_.ajax.ClientAjaxHandler;
import com.graphscape.commons.client.provider.default_.ajax.ClientAjaxMsgContext;
import com.graphscape.commons.comet.provider.default_.AjaxMsg;

/**
 * @author wu
 *
 */
public class ConnectedHandler extends ClientAjaxHandler{

	/**
	 * @param client
	 */
	public ConnectedHandler(AjaxMessageClient client) {
		super(client);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.fs.commons.webcomet.impl.mock.ClientAjaxHandler#handle(com.fs.commons.webcomet.impl.mock.ClientAjaxMsgContext)
	 */
	@Override
	public void handle(ClientAjaxMsgContext amc) {
		String sid = amc.am.getSessionId();
		this.client.conected(sid);
		
	}

}
