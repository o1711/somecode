/**
 * Dec 22, 2013
 */
package com.graphscape.largegraph.client.provider.default_;

import java.util.Iterator;

import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.lang.PropertiesI;
import com.graphscape.largegraph.client.ClientI;
import com.graphscape.largegraph.client.provider.default_.requester.EdgeIteratorRequester;
import com.graphscape.largegraph.client.provider.default_.requester.GraphIteratorRequester;
import com.graphscape.largegraph.core.Arrow;
import com.graphscape.largegraph.core.EdgeI;
import com.graphscape.largegraph.core.GraphI;
import com.graphscape.largegraph.core.Label;
import com.graphscape.largegraph.core.VertexI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class ClientVertex extends ClientElement implements VertexI {

	/**
	 * @param id
	 * @param t
	 */
	private ClientVertex(ClientI client, String id, PropertiesI<Object> t) {
		super(client, id, t);
	}

	/**
	 * @param id
	 * @param rt
	 * @return
	 */
	public static VertexI valueOf(ClientI client, String id, PropertiesI<Object> pts) {
		VertexI rt = new ClientVertex(client, id, pts);
		client.getVertexCache().put(rt);
		return rt;
		
	}

	@Override
	public Iterator<EdgeI> getEdgeIterator(Label label) {
		return new EdgeIteratorRequester(this.getId(), label, this.client).submit().get();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.largegraph.core.VertexI#getEdgeIterator(com.graphscape
	 * .largegraph.core.Direction, com.graphscape.largegraph.core.Label)
	 */
	@Override
	public Iterator<EdgeI> getEdgeIterator(Arrow dir, Label label) {
		throw new GsException("TODO");
	}

	@Override
	public Iterator<EdgeI> getEdgeIterator(Arrow dir) {
		throw new GsException("TODO");
	}

	@Override
	public Iterator<GraphI> getGraphIterator() {

		return new GraphIteratorRequester(this.getId(), this.client).submit().get();

	}

	@Override
	public void addGraph(String gid) {
		// TODO Auto-generated method stub

	}

}
