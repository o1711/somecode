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
public class MessageHandler extends ClientAjaxHandler{

	/**
	 * @param client
	 */
	public MessageHandler(AjaxMessageClient client) {
		super(client);
	}

	/* (non-Javadoc)
	 * @see com.fs.commons.webcomet.impl.mock.ClientAjaxHandler#handle(com.fs.commons.webcomet.impl.mock.ClientAjaxMsgContext)
	 */
	@Override
	public void handle(ClientAjaxMsgContext amc) {
		Object msgC = amc.am.getProperty(AjaxMsg.PK_CONTENT,true);
		
		this.client.rawMessageFromServer(msgC);
		
	}

}
