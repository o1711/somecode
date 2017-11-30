/**
 * Dec 8, 2013
 */
package com.graphscape.largegraph.core.provider.blueprint;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.graphscape.commons.lang.GsException;
import com.graphscape.largegraph.core.Arrow;
import com.graphscape.largegraph.core.EdgeI;
import com.graphscape.largegraph.core.GraphI;
import com.graphscape.largegraph.core.Label;
import com.graphscape.largegraph.core.VertexI;
import com.graphscape.largegraph.core.provider.blueprint.iterators.EdgeIterator;
import com.graphscape.largegraph.core.provider.blueprint.iterators.GraphIterator;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class DefaultVertex extends ElementWrapper<Vertex> implements VertexI {
	public static final Map<Arrow, com.tinkerpop.blueprints.Direction> D2D = new HashMap<Arrow, com.tinkerpop.blueprints.Direction>();
	static {
		D2D.put(Arrow.HEAD, com.tinkerpop.blueprints.Direction.IN);
		D2D.put(Arrow.TAIL, com.tinkerpop.blueprints.Direction.OUT);
		D2D.put(Arrow.BOTH, com.tinkerpop.blueprints.Direction.BOTH);

	}

	/**
	 * @param t
	 */
	private DefaultVertex(Vertex t, DefaultLargeGraph lg) {
		super(t, lg);
	}

	public static VertexI valueOf(Vertex t, DefaultLargeGraph lg) {
		if (t == null) {
			return null;
		}
		return new DefaultVertex(t, lg);
	}

	@Override
	public Iterator<EdgeI> getEdgeIterator(Label label) {
		return this.getEdgeIterator(Arrow.BOTH, label);
	}

	@Override
	public Iterator<EdgeI> getEdgeIterator(Arrow dir) {
		throw new GsException("TODO");
	}

	@Override
	public Iterator<GraphI> getGraphIterator() {

		Iterator<Vertex> vIt = this.largeGraph.vertex2ShapeIndex.get(DefaultGraph.IK_VERTEX_ID, this.getId())
				.iterator();

		Iterator<GraphI> rt = new GraphIterator(this.largeGraph, vIt);
		return rt;

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
		String[] labels = new String[] { label.toString() };

		Iterable<Edge> eIt = this.target.getEdges(D2D.get(dir), labels);
		Iterator<EdgeI> rt = new EdgeIterator(eIt.iterator(), this.largeGraph);
		return rt;
	}

	@Override
	public void addGraph(String gid) {
		Vertex gV = this.graph.getVertex(gid);
		GraphI g = DefaultGraph.valueOf(gV, this.largeGraph);
		g.addVertex(this.getId());
	}
}
