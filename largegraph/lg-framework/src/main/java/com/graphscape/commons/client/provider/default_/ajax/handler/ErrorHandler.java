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
public class ErrorHandler extends ClientAjaxHandler{

	/**
	 * @param client
	 */
	public ErrorHandler(AjaxMessageClient client) {
		super(client);
	}

	@Override
	public void handle(ClientAjaxMsgContext amc) {
		
		this.client.errorFromServer();
	}

}
