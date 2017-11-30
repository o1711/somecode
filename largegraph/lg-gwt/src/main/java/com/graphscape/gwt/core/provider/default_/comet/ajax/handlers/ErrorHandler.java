/**
 *  
 */
package com.graphscape.gwt.core.provider.default_.comet.ajax.handlers;

import com.graphscape.gwt.core.provider.default_.comet.ajax.AjaxGomet;
import com.graphscape.gwt.core.provider.default_.comet.ajax.AjaxMsg;
import com.graphscape.gwt.core.provider.default_.comet.ajax.ClientAjaxHandler;
import com.graphscape.gwt.core.provider.default_.comet.ajax.ClientAjaxMsgContext;

/**
 * @author wu
 * 
 */
public class ErrorHandler extends ClientAjaxHandler {

	/**
	 * @param client
	 */
	public ErrorHandler(AjaxGomet client) {
		super(client);
	}

	@Override
	public void handle(ClientAjaxMsgContext amc) {
		
		
		String errorCode = amc.am.getProperty(AjaxMsg.PK_ERROR_CODE,true);
		String errorMsg = amc.am.getProperty(AjaxMsg.PK_ERROR_MSG);
		
		this.client.errorFromServer(errorCode,errorMsg);
	}

}
