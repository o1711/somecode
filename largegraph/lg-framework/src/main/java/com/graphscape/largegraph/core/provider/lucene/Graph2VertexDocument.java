/**
 * Dec 31, 2013
 */
package com.graphscape.largegraph.core.provider.lucene;

import org.apache.lucene.document.Document;

import com.graphscape.largegraph.core.provider.lucene.document.DocumentFactory;
import com.graphscape.largegraph.core.provider.lucene.document.DocumentType;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class Graph2VertexDocument extends ElementDocument {
	public static final String PK_GRAPHID = "graphId";
	public static final String PK_VERTEXID = "vertexId";
	
	public Graph2VertexDocument(DocumentFactory factory, Document t) {
		super(factory, DocumentType.GRAPH2VERTEX, t);
	}

}
