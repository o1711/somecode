/**
 * 2013 ����11:15:34
 */
package com.graphscape.largegraph.core.provider.blueprint;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.configuration.MapConfiguration;

import com.graphscape.commons.configuration.ConfigurationI;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.lang.PropertiesI;
import com.graphscape.commons.lang.provider.default_.DefaultProperties;
import com.graphscape.largegraph.core.EdgeI;
import com.graphscape.largegraph.core.GraphI;
import com.graphscape.largegraph.core.Label;
import com.graphscape.largegraph.core.VertexI;
import com.graphscape.largegraph.core.provider.support.LargeGraphSupport;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.GraphFactory;
import com.tinkerpop.blueprints.Index;
import com.tinkerpop.blueprints.IndexableGraph;
import com.tinkerpop.blueprints.Vertex;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class DefaultLargeGraph extends LargeGraphSupport {
	protected IndexableGraph graph;

	protected Index<Vertex> vertex2ShapeIndex;
	protected Index<Vertex> graph2VertexIndex;

	public DefaultLargeGraph() {
	}

	@Override
	public void config(ConfigurationI cfg) {
		super.config(cfg);

		Map<String, Object> map = new HashMap<String, Object>();

		map.putAll(cfg.getChildConfiguration("blueprints", true).getAsMap());

		MapConfiguration configuration = new MapConfiguration(map);

		this.graph = (IndexableGraph) GraphFactory.open(configuration);

		this.vertex2ShapeIndex = this.graph.createIndex("vertex2Shape", Vertex.class);
		this.graph2VertexIndex = this.graph.createIndex("shape2Vertex", Vertex.class);

	}

	@Override
	public GraphI getGraph(String id) {
		Vertex r = this.graph.getVertex(id);
		return DefaultGraph.valueOf(r, this);
	}

	@Override
	public GraphI addGraph(PropertiesI<Object> pts) {
		String id = UUID.randomUUID().toString();
		Vertex r = this.graph.addVertex(id);

		return DefaultGraph.valueOf(r, this);

	}

	public Vertex getPropertiesVertex() {
		Vertex pts = this.graph.getVertex("0");

		if (pts == null) {
			pts = this.graph.addVertex("0");
		}

		return pts;

	}

	@Override
	public VertexI getVertex(String id) {

		return getVertex(id, false);
	}

	@Override
	public VertexI addVertex() {
		return this.addVertex(new DefaultProperties<Object>());
	}

	@Override
	public EdgeI addEdge(Label lable, String fromId, String toId) {
		Vertex from = this.graph.getVertex(fromId);
		Vertex to = this.graph.getVertex(toId);
		String id = UUID.randomUUID().toString();
		Edge eg = this.graph.addEdge(id, from, to, lable.getName());
		return DefaultEdge.valueOf(eg, this);
	}

	@Override
	public EdgeI getEdge(String id) {

		throw new GsException("todo");
	}

	@Override
	public EdgeI getEdge(String id, boolean force) {

		throw new GsException("todo");
	}

	@Override
	public VertexI addVertex(PropertiesI<Object> pts) {
		String id = UUID.randomUUID().toString();
		Vertex vt = this.graph.addVertex(id);
		for (String key : pts.keyList()) {
			Object value = pts.getProperty(key);
			vt.setProperty(key, value);
		}
		return DefaultVertex.valueOf(vt, this);
	}

	@Override
	public VertexI getVertex(String id, boolean force) {

		Vertex v = this.graph.getVertex(id);
		if (v == null) {
			if (force) {
				throw new GsException("no vertex with id:" + id);
			}
			return null;
		}

		VertexI rt = DefaultVertex.valueOf(v, this);
		return rt;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.largegraph.core.LargeGraphI#deleteGraph(java.lang.String)
	 */
	@Override
	public void deleteGraph(String gId) {
		Vertex v = this.graph.getVertex(gId);
		this.graph.removeVertex(v);
	}

	/* (non-Javadoc)
	 * @see com.graphscape.largegraph.core.LargeGraphI#getRootGraph()
	 */
	@Override
	public GraphI getRootGraph() {
		throw new GsException("TODO");
	}

}
