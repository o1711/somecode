/**
 * Dec 16, 2013
 */
package com.graphscape.largegraph.server;

import com.graphscape.commons.concurrent.FiniteBlockingQueueI;
import com.graphscape.commons.lang.support.HasAttributeSupport;
import com.graphscape.commons.message.MessageI;
import com.graphscape.largegraph.core.GraphI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class SessionContext extends HasAttributeSupport {

	FiniteBlockingQueueI<MessageI> outputQueue;

	private GraphI graph;

	/**
	 * @return the graph
	 */
	public GraphI getGraph() {
		return graph;
	}

	public SessionContext(GraphI g, FiniteBlockingQueueI<MessageI> oq) {
		this.graph = g;
		this.outputQueue = oq;
	}

	public void write(MessageI msg) {
		this.outputQueue.put(msg);
	}

}
