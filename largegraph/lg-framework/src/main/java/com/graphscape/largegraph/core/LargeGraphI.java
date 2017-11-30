/**
 * 2013 ����11:14:44
 */
package com.graphscape.largegraph.core;

import com.graphscape.commons.concurrent.FutureI;
import com.graphscape.commons.handling.ProcessorI;
import com.graphscape.commons.lang.LifeCycleI;
import com.graphscape.commons.lang.PropertiesI;
import com.graphscape.commons.message.MessageI;

/**
 * This is the graph based message handling entry point.
 * <p>
 * 
 * @author wuzhen0808@gmail.com
 * 
 */
public interface LargeGraphI extends LifeCycleI {

	public GraphI getRootGraph();
	
	public GraphI getGraph(String id);

	public GraphI addGraph(PropertiesI<Object> pts);

	public void deleteGraph(String gId);

	public FutureI<MessageI> putMessage(MessageI msg);

	public VertexI getVertex(String id);

	public VertexI getVertex(String id, boolean force);

	public VertexI addVertex();

	public VertexI addVertex(PropertiesI<Object> pts);
	
	public EdgeI getEdge(String id);

	public EdgeI getEdge(String id, boolean force);

	public EdgeI addEdge(Label label, String fromId, String toId);

	/**
	 * @return
	 */
	public ProcessorI<MessageI, MessageI> getMessageProcessor();

	/**
	 * @param gId
	 */

}
