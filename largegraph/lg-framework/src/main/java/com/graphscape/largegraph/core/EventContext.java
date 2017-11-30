/**
 * 
 */
package com.graphscape.largegraph.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.graphscape.commons.handling.spi.HandlingProviderI;
import com.graphscape.commons.message.MessageI;
import com.graphscape.commons.message.support.MessageContextSupport;

/**
 * @author wuzhen
 * 
 */
public class EventContext extends MessageContextSupport {

	public static final Logger LOG = LoggerFactory.getLogger(EventContext.class);

	private LargeGraphI largeGraph;

	/**
	 * @return the largeGraph
	 */
	public LargeGraphI getLargeGraph() {
		return largeGraph;
	}

	/**
	 * 
	 * @param queue
	 *            the queue name of the message got from.
	 * @param msg
	 *            the message itself.
	 * @param ef
	 *            Event factory
	 * @param et
	 *            tracer.
	 */
	public EventContext(MessageI msg, HandlingProviderI<MessageI, MessageI, EventContext> mspi, LargeGraphI lg) {
		super(msg, mspi);
		this.largeGraph = lg;
	}
	
	public GraphI getGraph(){
		String gid = this.getInputData().getHeader("_graphId",true);
		return this.largeGraph.getGraph(gid);
	}

}
