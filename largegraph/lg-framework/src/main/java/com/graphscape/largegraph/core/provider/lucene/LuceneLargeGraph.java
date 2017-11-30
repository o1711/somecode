/**
 * Dec 30, 2013
 */
package com.graphscape.largegraph.core.provider.lucene;

import java.util.UUID;

import com.graphscape.commons.configuration.ConfigurationI;
import com.graphscape.commons.lang.PropertiesI;
import com.graphscape.commons.lang.provider.default_.DefaultProperties;
import com.graphscape.largegraph.core.EdgeI;
import com.graphscape.largegraph.core.GraphI;
import com.graphscape.largegraph.core.Label;
import com.graphscape.largegraph.core.VertexI;
import com.graphscape.largegraph.core.provider.lucene.document.DocumentFactory;
import com.graphscape.largegraph.core.provider.lucene.document.operations.AddDocumentOperation;
import com.graphscape.largegraph.core.provider.lucene.document.operations.GetElementOperation;
import com.graphscape.largegraph.core.provider.support.LargeGraphSupport;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class LuceneLargeGraph extends LargeGraphSupport {

	DocumentFactory documents = new DocumentFactory();

	@Override
	public void config(ConfigurationI cfg) {
		// TODO Auto-generated method stub
		super.config(cfg);

	}

	@Override
	public GraphI getGraph(String id) {

		return new GetElementOperation<GraphI>(this.documents, GraphI.class, id, false).execute();
	}

	@Override
	public GraphI addGraph(PropertiesI<Object> pts) {
		String id = UUID.randomUUID().toString();
		return new AddDocumentOperation<GraphI>(this.documents, GraphI.class, id, pts).execute();
	}

	@Override
	public void deleteGraph(String gId) {

	}

	@Override
	public VertexI getVertex(String id, boolean force) {
		return new GetElementOperation<VertexI>(this.documents, VertexI.class, id, force).execute();

	}

	@Override
	public VertexI addVertex(PropertiesI<Object> pts) {
		String id = UUID.randomUUID().toString();
		return new AddDocumentOperation<VertexI>(this.documents, VertexI.class, id, pts).execute();
	}

	@Override
	public EdgeI getEdge(String id) {
		return this.getEdge(id, false);
	}

	@Override
	public EdgeI getEdge(String id, boolean force) {
		return new GetElementOperation<EdgeI>(this.documents, EdgeI.class, id, force).execute();
	}

	@Override
	public EdgeI addEdge(Label label, String fromId, String toId) {
		String id = UUID.randomUUID().toString();
		PropertiesI<Object> pts = new DefaultProperties<Object>();
		pts.setProperty(LuceneEdge.PK_LABEL, label.toString());
		pts.setProperty(LuceneEdge.PK_TAILVERTEXID, fromId);
		pts.setProperty(LuceneEdge.PK_HEADVERTEXID, toId);
		return new AddDocumentOperation<EdgeI>(this.documents, EdgeI.class, id, pts).execute();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.largegraph.core.LargeGraphI#getRootGraph()
	 */
	@Override
	public GraphI getRootGraph() {
		
		GraphI rt = this.getGraph("0");///.documents.reader().getElement(GraphI.class, "0", false);
		
		if (rt != null) {
			return rt;
		}

		PropertiesI<Object> pts = new DefaultProperties<Object>();
		pts.setProperty("user", "root");
		pts.setProperty("password", "root");

		GraphI rg = new AddDocumentOperation<GraphI>(this.documents, GraphI.class, "0", pts).execute();

		return rg;
	}

}
