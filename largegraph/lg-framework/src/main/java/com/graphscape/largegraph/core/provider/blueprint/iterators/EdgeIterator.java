/**
 * Dec 28, 2013
 */
package com.graphscape.largegraph.core.provider.blueprint.iterators;

import java.util.Iterator;

import com.graphscape.commons.util.ProxyIterator;
import com.graphscape.largegraph.core.EdgeI;
import com.graphscape.largegraph.core.provider.blueprint.DefaultEdge;
import com.graphscape.largegraph.core.provider.blueprint.DefaultLargeGraph;
import com.tinkerpop.blueprints.Edge;

public class EdgeIterator extends ProxyIterator<Edge, EdgeI> implements Iterator<EdgeI> {
	DefaultLargeGraph lg;

	public EdgeIterator(Iterator<Edge> eIt, DefaultLargeGraph lg) {
		super(eIt);
		this.lg = lg;

	}

	@Override
	public EdgeI convert(Edge v) {
		return DefaultEdge.valueOf(v, this.lg);
	}

}