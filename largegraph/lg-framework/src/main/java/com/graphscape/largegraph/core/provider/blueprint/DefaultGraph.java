/**
 * 2013 ����11:15:34
 */
package com.graphscape.largegraph.core.provider.blueprint;

import java.util.Iterator;

import com.graphscape.largegraph.core.GraphI;
import com.graphscape.largegraph.core.VertexI;
import com.graphscape.largegraph.core.provider.blueprint.iterators.VertexIterator;
import com.tinkerpop.blueprints.CloseableIterable;
import com.tinkerpop.blueprints.Index;
import com.tinkerpop.blueprints.Vertex;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class DefaultGraph extends ElementWrapper<Vertex> implements GraphI {
	public static final String IK_GRAPH_ID = "graphId";
	public static final String IK_VERTEX_ID = "vertexId";
	

	private DefaultGraph(Vertex v, DefaultLargeGraph lg) {
		super(v, lg);

	}

	public static GraphI valueOf(Vertex v, DefaultLargeGraph lg) {
		return new DefaultGraph(v, lg);
	}

	@Override
	public void addVertex(String vid) {
		Vertex v = this.graph.getVertex(vid);		
		this.largeGraph.graph2VertexIndex.put(IK_GRAPH_ID, this.getId(), v);
		this.largeGraph.vertex2ShapeIndex.put(IK_VERTEX_ID, vid, this.target);
	}

	@Override
	public VertexI removeVertex(String vid) {
		Vertex v = this.graph.getVertex(vid);
		this.largeGraph.graph2VertexIndex.remove(IK_GRAPH_ID, this.getId(), v);
		this.largeGraph.vertex2ShapeIndex.remove(IK_VERTEX_ID, vid, this.target);//		
		return DefaultVertex.valueOf(v, this.largeGraph);

	}

	@Override
	public void clear() {
		Iterator<VertexI> vIt = this.getVertexIterator();
		while(vIt.hasNext()){
			VertexI v = vIt.next();
			this.removeVertex(v.getId());
		}
	}

	@Override
	public Iterator<VertexI> getVertexIterator() {
		CloseableIterable<Vertex> vIt = this.largeGraph.graph2VertexIndex.get(IK_GRAPH_ID, this.getId());
		Iterator<Vertex> vIt2 = vIt.iterator();
		Iterator<VertexI> rt = new VertexIterator(this.largeGraph, vIt2);
		return rt;
	}

	@Override
	public void destroy() {
		this.clear();
		this.graph.removeVertex(this.target);		
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
