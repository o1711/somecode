package com.graphscape.largegraph.core.provider.blueprint.iterators;

import java.util.Iterator;

import com.graphscape.commons.util.ProxyIterator;
import com.graphscape.largegraph.core.GraphI;
import com.graphscape.largegraph.core.provider.blueprint.DefaultGraph;
import com.graphscape.largegraph.core.provider.blueprint.DefaultLargeGraph;
import com.tinkerpop.blueprints.Vertex;

public class GraphIterator extends ProxyIterator<Vertex, GraphI> implements Iterator<GraphI> {

	DefaultLargeGraph lg;

	public GraphIterator(DefaultLargeGraph lg, Iterator<Vertex> vIt) {
		super(vIt);
		this.lg = lg;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.largegraph.core.provider.default_.ProxyIterator#convert
	 * (java.lang.Object)
	 */
	@Override
	protected GraphI convert(Vertex s) {
		// TODO Auto-generated method stub
		return DefaultGraph.valueOf(s, lg);
	}

}
