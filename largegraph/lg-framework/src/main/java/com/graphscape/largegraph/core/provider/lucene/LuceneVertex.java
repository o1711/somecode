/**
 * Dec 30, 2013
 */
package com.graphscape.largegraph.core.provider.lucene;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.lucene.document.Document;

import com.graphscape.commons.lang.PropertiesI;
import com.graphscape.commons.lang.provider.default_.DefaultProperties;
import com.graphscape.largegraph.core.Arrow;
import com.graphscape.largegraph.core.EdgeI;
import com.graphscape.largegraph.core.GraphI;
import com.graphscape.largegraph.core.Label;
import com.graphscape.largegraph.core.VertexI;
import com.graphscape.largegraph.core.provider.lucene.document.DocumentFactory;
import com.graphscape.largegraph.core.provider.lucene.document.DocumentType;
import com.graphscape.largegraph.core.provider.lucene.document.operations.GetEdgeListOperation;
import com.graphscape.largegraph.core.provider.lucene.document.operations.GetElementListOperation;
import com.graphscape.largegraph.core.provider.lucene.document.operations.GetElementOperation;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class LuceneVertex extends ElementDocument implements VertexI {

	public LuceneVertex(DocumentFactory factory, Document doc) {
		super(factory, DocumentType.VERTEX, doc);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.largegraph.core.VertexI#getGraphIterator()
	 */
	@Override
	public Iterator<GraphI> getGraphIterator() {
		PropertiesI<Object> pts = new DefaultProperties<Object>();

		pts.setProperty(Graph2VertexDocument.PK_VERTEXID, this.getId());

		List<Graph2VertexDocument> rL = new GetElementListOperation<Graph2VertexDocument>(this.factory,
				Graph2VertexDocument.class, pts).execute();
		List<GraphI> gL = new ArrayList<GraphI>();
		for (Graph2VertexDocument doc : rL) {
			String gId = (String) doc.getProperty(Graph2VertexDocument.PK_GRAPHID, true);
			GraphI g = new GetElementOperation<GraphI>(this.factory, GraphI.class, gId, true).execute();
			gL.add(g);
		}
		return gL.iterator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.largegraph.core.VertexI#getEdgeIterator(com.graphscape
	 * .largegraph.core.Label)
	 */
	@Override
	public Iterator<EdgeI> getEdgeIterator(Label label) {
		return this.getEdgeIterator(Arrow.BOTH, label);
	}

	public Iterator<EdgeI> doGetEdgeIterator(Label label, Arrow dir) {
		List<EdgeI> rtL = new GetEdgeListOperation(this.factory, label, dir, this.getId()).execute();
		return rtL.iterator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.largegraph.core.VertexI#getEdgeIterator(com.graphscape
	 * .largegraph.core.Arrow, com.graphscape.largegraph.core.Label)
	 */
	@Override
	public Iterator<EdgeI> getEdgeIterator(Arrow dir, Label label) {
		return this.doGetEdgeIterator(label, dir);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.largegraph.core.VertexI#getEdgeIterator(com.graphscape
	 * .largegraph.core.Arrow)
	 */
	@Override
	public Iterator<EdgeI> getEdgeIterator(Arrow dir) {
		return this.doGetEdgeIterator(null, dir);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.largegraph.core.VertexI#addGraph(java.lang.String)
	 */
	@Override
	public void addGraph(String gid) {
		GraphI g = new GetElementOperation<GraphI>(this.factory, GraphI.class, gid, true).execute();

		g.addVertex(this.getId());
	}
}
