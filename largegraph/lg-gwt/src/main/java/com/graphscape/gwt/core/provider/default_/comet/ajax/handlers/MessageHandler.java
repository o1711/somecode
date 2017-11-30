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
public class MessageHandler extends ClientAjaxHandler{

	/**
	 * @param client
	 */
	public MessageHandler(AjaxGomet client) {
		super(client);
	}

	/* (non-Javadoc)
	 * @see com.fs.commons.webcomet.impl.mock.ClientAjaxHandler#handle(com.fs.commons.webcomet.impl.mock.ClientAjaxMsgContext)
	 */
	@Override
	public void handle(ClientAjaxMsgContext amc) {
		String msg = amc.am.getProperty(AjaxMsg.PK_TEXTMESSAGE,true);
		this.client.messageFromServer(msg);
		
	}

}
