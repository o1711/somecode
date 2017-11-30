/**
 * Dec 16, 2013
 */
package com.graphscape.largegraph.client.provider.default_;

import java.util.concurrent.Future;

import com.graphscape.commons.concurrent.FutureI;
import com.graphscape.commons.handling.ProcessorI;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.lang.LifeCycleI;
import com.graphscape.commons.lang.PropertiesI;
import com.graphscape.commons.lang.provider.default_.DefaultProperties;
import com.graphscape.commons.message.MessageI;
import com.graphscape.largegraph.client.ClientI;
import com.graphscape.largegraph.client.provider.default_.requester.AddEdgeRequester;
import com.graphscape.largegraph.client.provider.default_.requester.AddGraphRequester;
import com.graphscape.largegraph.client.provider.default_.requester.AddVertexRequester;
import com.graphscape.largegraph.client.provider.default_.requester.GetGraphCallback;
import com.graphscape.largegraph.client.provider.default_.requester.GetVertexRequester;
import com.graphscape.largegraph.client.provider.default_.requester.PutMessageRequester;
import com.graphscape.largegraph.client.provider.support.ClientObject;
import com.graphscape.largegraph.core.EdgeI;
import com.graphscape.largegraph.core.GraphI;
import com.graphscape.largegraph.core.Label;
import com.graphscape.largegraph.core.LargeGraphI;
import com.graphscape.largegraph.core.VertexI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class ClientLargeGraph extends ClientObject implements LargeGraphI {

	public ClientLargeGraph(ClientI client) {
		super(client);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.largegraph.core.LargeGraphI#putMessage(com.graphscape.
	 * commons.message.MessageI)
	 */
	@Override
	public FutureI<MessageI> putMessage(MessageI msg) {
		return new PutMessageRequester(msg, this.client).submit();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.largegraph.core.LargeGraphI#getMessageProcessor()
	 */
	@Override
	public ProcessorI<MessageI, MessageI> getMessageProcessor() {

		throw new GsException("TODO");
	}

	@Override
	public GraphI getGraph(String id) {

		return new GetGraphCallback(id, this.client).submit().get();
	}

	@Override
	public GraphI addGraph(PropertiesI<Object> pts) {
		return new AddGraphRequester(pts, this.client).submit().get();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.largegraph.core.LargeGraphI#getProperty(java.lang.String)
	 */
	@Override
	public Object getProperty(String key) {
		throw new GsException("TODO");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.largegraph.core.LargeGraphI#setProperty(java.lang.String,
	 * java.lang.Object)
	 */
	@Override
	public void setProperty(String key, Object value) {
		throw new GsException("TODO");

	}

	@Override
	public VertexI getVertex(String id) {
		VertexI rt = this.getVertex(id, false);
		return rt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.largegraph.core.LargeGraphI#addEdge(com.graphscape.largegraph
	 * .core.Label, java.lang.String, java.lang.String)
	 */
	@Override
	public EdgeI addEdge(Label label, String fromId, String toId) {
		return new AddEdgeRequester(fromId, label, toId, this.client).submit().get();
	}

	@Override
	public VertexI getVertex(String id, boolean force) {
		return new GetVertexRequester(id, this.client).submit().get();
	}

	@Override
	public VertexI addVertex() {
		return this.addVertex(new DefaultProperties<Object>());
	}

	@Override
	public VertexI addVertex(PropertiesI<Object> pts) {
		return new AddVertexRequester(pts, this.client).submit().get();
	}

	@Override
	public EdgeI getEdge(String id) {
		throw new GsException("TODO");
	}

	@Override
	public EdgeI getEdge(String id, boolean force) {
		throw new GsException("TODO");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.largegraph.core.LargeGraphI#deleteGraph(java.lang.String)
	 */
	@Override
	public void deleteGraph(String gId) {
		// throw new GsException("TODO");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.lang.LifeCycleI#start()
	 */
	@Override
	public void start() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.lang.LifeCycleI#join()
	 */
	@Override
	public Future<LifeCycleI> join() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.lang.LifeCycleI#shutdown()
	 */
	@Override
	public void shutdown() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.graphscape.largegraph.core.LargeGraphI#getRootGraph()
	 */
	@Override
	public GraphI getRootGraph() {
		throw new GsException("TODO");
	}

}
