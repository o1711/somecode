/**
 * Dec 16, 2013
 */
package com.graphscape.largegraph.client.provider.default_;

import java.util.Iterator;

import com.graphscape.commons.lang.PropertiesI;
import com.graphscape.largegraph.client.ClientI;
import com.graphscape.largegraph.client.provider.default_.requester.GraphAddVertexRequester;
import com.graphscape.largegraph.client.provider.default_.requester.GraphRemoveVertexRequester;
import com.graphscape.largegraph.client.provider.default_.requester.VertexIteratorRequester;
import com.graphscape.largegraph.core.GraphI;
import com.graphscape.largegraph.core.VertexI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class ClientGraph extends ClientElement implements GraphI {

	private ClientGraph(ClientI client, String id, PropertiesI<Object> pts) {
		super(client, id, pts);
	}

	public static ClientGraph valueOf(String id, PropertiesI<Object> pts, ClientI lg) {
		return new ClientGraph(lg, id, pts);
	}

	@Override
	public void addVertex(String vid) {
		new GraphAddVertexRequester(this.id, vid, this.client).submit().get();
	}

	@Override
	public VertexI removeVertex(String vid) {
		return new GraphRemoveVertexRequester(this.id, vid, this.client).submit().get();
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public Iterator<VertexI> getVertexIterator() {
		return new VertexIteratorRequester(this.getId(), this.client).submit().get();

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.graphscape.largegraph.core.GraphI#isContainsGraph(java.lang.String)
	 */
	@Override
	public boolean isContainsGraph(String gid) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.graphscape.largegraph.core.GraphI#getOverlapedGraphIterator()
	 */
	@Override
	public Iterator<GraphI> getOverlapedGraphIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.graphscape.largegraph.core.GraphI#getNeiborGraphIterator()
	 */
	@Override
	public Iterator<GraphI> getNeiborGraphIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.graphscape.largegraph.core.GraphI#getInnerGraphIterator()
	 */
	@Override
	public Iterator<GraphI> getInnerGraphIterator() {
		// TODO Auto-generated method stub
		return null;
	}

}
