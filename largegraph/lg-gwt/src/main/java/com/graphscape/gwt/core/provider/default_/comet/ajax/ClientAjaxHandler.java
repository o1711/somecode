/**
 *  
 */
package com.graphscape.gwt.core.provider.default_.comet.ajax;

import com.graphscape.gwt.core.provider.default_.comet.ajax.AjaxGomet;
import com.graphscape.gwt.core.provider.default_.comet.ajax.ClientAjaxMsgContext;


/**
 * @author wu
 * 
 */
public abstract class ClientAjaxHandler {
	protected AjaxGomet client;

	public ClientAjaxHandler(AjaxGomet client) {
		this.client = client;
	}

	public abstract void handle(ClientAjaxMsgContext amc);
}
