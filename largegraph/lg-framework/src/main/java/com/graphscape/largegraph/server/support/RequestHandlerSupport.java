/**
 * Dec 16, 2013
 */
package com.graphscape.largegraph.server.support;

import com.graphscape.commons.lang.HandlerI;
import com.graphscape.largegraph.server.RequestContext;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public abstract class RequestHandlerSupport implements HandlerI<RequestContext> {
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.commons.message.MessageHandlerI#handle(com.graphscape.
	 * commons.message.MessageContextI)
	 */
	@Override
	public void handle(RequestContext mc) {
		this.handleInternal(mc);

	}

	protected abstract void handleInternal(RequestContext mc);
}
