/**
 * Dec 8, 2013
 */
package com.graphscape.largegraph.core.provider.blueprint.support;

import com.graphscape.largegraph.core.LargeGraphI;
import com.tinkerpop.blueprints.IndexableGraph;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class GraphObjectWrapper<T> {

	protected T target;

	protected LargeGraphI largeGraph;

	protected IndexableGraph graph;

	/**
	 * @param t
	 */
	protected GraphObjectWrapper(T t, IndexableGraph g, LargeGraphI lg) {
		this.target = t;
		this.graph = g;
		this.largeGraph = lg;
	}

}
