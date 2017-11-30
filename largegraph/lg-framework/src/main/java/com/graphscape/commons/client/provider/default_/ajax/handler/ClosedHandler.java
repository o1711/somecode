/**
 *  
 */
package com.graphscape.commons.client.provider.default_.ajax.handler;

import com.graphscape.commons.client.provider.default_.ajax.AjaxMessageClient;
import com.graphscape.commons.client.provider.default_.ajax.ClientAjaxHandler;
import com.graphscape.commons.client.provider.default_.ajax.ClientAjaxMsgContext;

/**
 * @author wu
 *
 */
public class ClosedHandler extends ClientAjaxHandler{

	/**
	 * @param client
	 */
	public ClosedHandler(AjaxMessageClient client) {
		super(client);
	}

	/* (non-Javadoc)
	 * @see com.fs.commons.webcomet.impl.mock.ClientAjaxHandler#handle(com.fs.commons.webcomet.impl.mock.ClientAjaxMsgContext)
	 */
	@Override
	public void handle(ClientAjaxMsgContext amc) {
		this.client.closedByServer();
	}

}
