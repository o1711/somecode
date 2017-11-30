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
public class ClosedHandler extends ClientAjaxHandler{

	/**
	 * @param client
	 */
	public ClosedHandler(AjaxGomet client) {
		super(client);
	}

	/* (non-Javadoc)
	 * @see com.fs.commons.webcomet.impl.mock.ClientAjaxHandler#handle(com.fs.commons.webcomet.impl.mock.ClientAjaxMsgContext)
	 */
	@Override
	public void handle(ClientAjaxMsgContext amc) {
		this.client.tryClosedByServer();
	}

}
