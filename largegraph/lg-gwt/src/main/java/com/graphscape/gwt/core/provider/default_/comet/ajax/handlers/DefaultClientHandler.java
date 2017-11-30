/**
 *  
 */
package com.graphscape.gwt.core.provider.default_.comet.ajax.handlers;


import com.graphscape.gwt.core.logger.UiLoggerFactory;
import com.graphscape.gwt.core.logger.UiLoggerI;
import com.graphscape.gwt.core.provider.default_.comet.ajax.AjaxGomet;
import com.graphscape.gwt.core.provider.default_.comet.ajax.ClientAjaxHandler;
import com.graphscape.gwt.core.provider.default_.comet.ajax.ClientAjaxMsgContext;
import com.graphscape.gwt.core.provider.default_.comet.ajax.handlers.DefaultClientHandler;


/**
 * @author wu
 * 
 */
public class DefaultClientHandler extends ClientAjaxHandler {

	public static final UiLoggerI LOG = UiLoggerFactory.getLogger(DefaultClientHandler.class);

	/**
	 * @param client
	 */
	public DefaultClientHandler(AjaxGomet client) {
		super(client);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.commons.webcomet.impl.mock.ClientAjaxHandler#handle(com.fs.commons.webcomet.impl
	 * .mock.ClientAjaxMsgContext)
	 */
	@Override
	public void handle(ClientAjaxMsgContext amc) {
		LOG.info("amc:" + amc);
	}

}
