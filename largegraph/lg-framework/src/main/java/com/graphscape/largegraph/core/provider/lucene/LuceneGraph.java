/**
 * Dec 30, 2013
 */
package com.graphscape.largegraph.core.provider.lucene;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.apache.lucene.document.Document;

import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.lang.PropertiesI;
import com.graphscape.commons.lang.provider.default_.DefaultProperties;
import com.graphscape.largegraph.core.GraphI;
import com.graphscape.largegraph.core.VertexI;
import com.graphscape.largegraph.core.provider.lucene.document.DocumentFactory;
import com.graphscape.largegraph.core.provider.lucene.document.DocumentType;
import com.graphscape.largegraph.core.provider.lucene.document.operations.AddDocumentOperation;
import com.graphscape.largegraph.core.provider.lucene.document.operations.GetElementListOperation;
import com.graphscape.largegraph.core.provider.lucene.document.operations.GetElementOperation;
import com.graphscape.largegraph.core.provider.lucene.document.operations.RemoveDocumentOperation;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class LuceneGraph extends ElementDocument implements GraphI {

	public LuceneGraph(DocumentFactory factory, Document doc) {
		super(factory, DocumentType.GRAPH, doc);
	}

	@Override
	public void addVertex(String vid) {
		String id = UUID.randomUUID().toString();
		PropertiesI<Object> pts = new DefaultProperties<Object>();
		pts.setProperty(Graph2VertexDocument.PK_GRAPHID, this.getId());
		pts.setProperty(Graph2VertexDocument.PK_VERTEXID, vid);
		new AddDocumentOperation<Graph2VertexDocument>(this.factory,Graph2VertexDocument.class, id, pts).execute();
		
	}

	@Override
	public VertexI removeVertex(String vid) {

		VertexI rt = new GetElementOperation<VertexI>(this.factory,VertexI.class, vid,true).execute();
		PropertiesI<Object> pts = new DefaultProperties<Object>();
		pts.setProperty(Graph2VertexDocument.PK_GRAPHID, this.getId());
		pts.setProperty(Graph2VertexDocument.PK_VERTEXID, vid);

		new RemoveDocumentOperation(this.factory,DocumentType.GRAPH2VERTEX, pts).execute();

		return rt;
	}

	@Override
	public void clear() {
		PropertiesI<Object> pts = new DefaultProperties<Object>();
		pts.setProperty(Graph2VertexDocument.PK_GRAPHID, this.getId());
		new RemoveDocumentOperation(this.factory,DocumentType.GRAPH2VERTEX, pts).execute();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.largegraph.core.GraphI#getVertexIterator()
	 */
	@Override
	public Iterator<VertexI> getVertexIterator() {
		PropertiesI<Object> pts = new DefaultProperties<Object>();

		pts.setProperty(Graph2VertexDocument.PK_GRAPHID, this.getId());

		List<Graph2VertexDocument> rL = new GetElementListOperation<Graph2VertexDocument>(this.factory,Graph2VertexDocument.class, pts).execute();
		List<VertexI> vL = new ArrayList<VertexI>();
		for (Graph2VertexDocument doc : rL) {
			String vId = (String) doc.getProperty("vertexId", true);
			VertexI v = new GetElementOperation<VertexI>(this.factory,VertexI.class, vId,true).execute();
			vL.add(v);
		}
		return vL.iterator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.largegraph.core.GraphI#destroy()
	 */
	@Override
	public void destroy() {
		PropertiesI<Object> pts = new DefaultProperties<Object>();
		pts.setProperty(Graph2VertexDocument.PK_GRAPHID, this.getId());
		new RemoveDocumentOperation(this.factory,DocumentType.GRAPH2VERTEX, pts).execute();
		new RemoveDocumentOperation(this.factory,DocumentType.GRAPH, this.getId()).execute();
		

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.largegraph.core.GraphI#isContainsGraph(java.lang.String)
	 */
	@Override
	public boolean isContainsGraph(String gid) {
		throw new GsException("todo");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.largegraph.core.GraphI#getOverlapedGraphIterator()
	 */
	@Override
	public Iterator<GraphI> getOverlapedGraphIterator() {

		throw new GsException("todo");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.largegraph.core.GraphI#getNeiborGraphIterator()
	 */
	@Override
	public Iterator<GraphI> getNeiborGraphIterator() {
		throw new GsException("todo");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.largegraph.core.GraphI#getInnerGraphIterator()
	 */
	@Override
	public Iterator<GraphI> getInnerGraphIterator() {
		throw new GsException("todo");
	}

}
