/**
 * Dec 28, 2013
 */
package com.graphscape.largegraph.core.provider.blueprint.iterators;

import java.util.Iterator;

import com.graphscape.commons.util.ProxyIterator;
import com.graphscape.largegraph.core.VertexI;
import com.graphscape.largegraph.core.provider.blueprint.DefaultLargeGraph;
import com.graphscape.largegraph.core.provider.blueprint.DefaultVertex;
import com.tinkerpop.blueprints.Vertex;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class VertexIterator extends ProxyIterator<Vertex, VertexI> {

	DefaultLargeGraph lg;

	public VertexIterator(DefaultLargeGraph lg, Iterator<Vertex> v) {
		super(v);
		this.lg = lg;
	}

	@Override
	protected VertexI convert(Vertex s) {

		return DefaultVertex.valueOf(s, lg);
	}

}
