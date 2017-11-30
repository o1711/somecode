/**
 *  
 */
package com.graphscape.commons.client.provider.default_.ajax.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.graphscape.commons.client.provider.default_.ajax.AjaxMessageClient;
import com.graphscape.commons.client.provider.default_.ajax.ClientAjaxHandler;
import com.graphscape.commons.client.provider.default_.ajax.ClientAjaxMsgContext;

/**
 * @author wu
 * 
 */
public class DefaultClientHandler extends ClientAjaxHandler {

	public static final Logger LOG = LoggerFactory.getLogger(DefaultClientHandler.class);

	/**
	 * @param client
	 */
	public DefaultClientHandler(AjaxMessageClient client) {
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
