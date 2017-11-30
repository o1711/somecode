/**
 * Dec 8, 2013
 */
package com.graphscape.largegraph.core.provider.blueprint;

import com.graphscape.largegraph.core.EdgeI;
import com.graphscape.largegraph.core.Label;
import com.graphscape.largegraph.core.LargeGraphI;
import com.graphscape.largegraph.core.VertexI;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.IndexableGraph;
import com.tinkerpop.blueprints.Vertex;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class DefaultEdge extends ElementWrapper<Edge> implements EdgeI {

	/**
	 * @param t
	 */
	private DefaultEdge(Edge t, DefaultLargeGraph lg) {
		super(t, lg);
	}

	public static EdgeI valueOf(Edge v, DefaultLargeGraph lg) {
		return new DefaultEdge(v, lg);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.largegraph.core.EdgeI#getStartVertex()
	 */
	@Override
	public VertexI getTailVertex() {
		Vertex v = this.target.getVertex(Direction.OUT);

		return DefaultVertex.valueOf(v, this.largeGraph);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.largegraph.core.EdgeI#getEndVertex()
	 */
	@Override
	public VertexI getHeadVertex() {
		Vertex v = this.target.getVertex(Direction.IN);
		return DefaultVertex.valueOf(v, this.largeGraph);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.largegraph.core.EdgeI#getLabel()
	 */
	@Override
	public Label getLabel() {
		// TODO Auto-generated method stub
		String l = this.target.getLabel();
		return Label.valueOf(l);

	}

}
