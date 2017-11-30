/**
 * 
 */
package com.graphscape.commons.http.support;

import com.graphscape.commons.http.HttpRequestContextI;
import com.graphscape.commons.http.HttpRequestHandlerI;
import com.graphscape.commons.http.HttpRequestI;
import com.graphscape.commons.lang.GsException;

/**
 * @author wuzhen
 * 
 */
public abstract class HttpRequestHandlerSupport implements HttpRequestHandlerI {

	@Override
	public void messageReceived(HttpRequestContextI ctx) {
		try {
			this.messageReceivedInternal(ctx.getRequest(), ctx);
			ctx.endOfResponse();
		} catch (Exception e) {
			throw GsException.toRuntimeException(e);
		}
	}

	public abstract void messageReceivedInternal(HttpRequestI req,
			HttpRequestContextI ctx) throws Exception;

}
