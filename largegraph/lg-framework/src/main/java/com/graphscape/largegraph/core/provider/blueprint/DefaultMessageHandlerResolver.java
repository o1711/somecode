/**
 * Dec 9, 2013
 */
package com.graphscape.largegraph.core.provider.blueprint;

import com.graphscape.commons.handling.support.HandlerResolverSupport;
import com.graphscape.commons.lang.HandlerI;
import com.graphscape.commons.message.MessageI;
import com.graphscape.largegraph.core.EventContext;
import com.graphscape.largegraph.core.LargeGraphI;
import com.graphscape.largegraph.core.VertexI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class DefaultMessageHandlerResolver extends HandlerResolverSupport<MessageI, MessageI, EventContext> {

	/**
	 * @param def
	 */
	public DefaultMessageHandlerResolver() {
		super(null);
	}

	@Override
	protected HandlerI<EventContext> doResolve(EventContext mc) {
		LargeGraphI lg = mc.getLargeGraph();
		MessageI msg = mc.getInputData();
		String vid = msg.getHeader("vertexId", true);

		VertexI vt = lg.getVertex(vid);

		return vt.getEventHandler();
	}

}
