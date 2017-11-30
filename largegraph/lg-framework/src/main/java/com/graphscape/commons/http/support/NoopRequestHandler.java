/**
 * Dec 14, 2013
 */
package com.graphscape.commons.http.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.graphscape.commons.http.HttpRequestContextI;
import com.graphscape.commons.http.HttpRequestHandlerI;
import com.graphscape.commons.http.ResponseStatus;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class NoopRequestHandler implements HttpRequestHandlerI {
	private static final Logger LOG = LoggerFactory
			.getLogger(NoopRequestHandler.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.http.HttpRequestHandlerI#messageReceived(com.
	 * graphscape.commons.http.HttpRequestContextI)
	 */
	@Override
	public void messageReceived(HttpRequestContextI req) {
		if (LOG.isWarnEnabled()) {
			LOG.warn("messageReceived,returning 404 for request uri:"
					+ req.getRequest().getUri());
		}
		req.response(ResponseStatus.NOT_FOUND);
	}

}
