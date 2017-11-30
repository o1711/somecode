/**
 * Dec 7, 2013
 */
package com.graphscape.largegraph.core.support;

import com.graphscape.commons.lang.HandlerI;
import com.graphscape.largegraph.core.EventContext;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public abstract class NodeMessageHandlerSupport implements
		HandlerI<EventContext> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.commons.message.MessageHandlerI#handle(com.graphscape.
	 * commons.message.MessageContext)
	 */
	@Override
	public void handle(EventContext mc) {
		this.handleInternal(mc);
	}

	protected abstract void handleInternal(EventContext mc);

}
