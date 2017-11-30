/**
 *  
 */
package com.graphscape.commons.client.provider.default_.ajax;




/**
 * @author wu
 * 
 */
public abstract class ClientAjaxHandler {
	protected AjaxMessageClient client;

	public ClientAjaxHandler(AjaxMessageClient client) {
		this.client = client;
	}

	public abstract void handle(ClientAjaxMsgContext amc);
}
