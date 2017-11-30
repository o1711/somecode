/**
 * Dec 30, 2013
 */
package com.graphscape.largegraph.core.provider.lucene;

import org.apache.lucene.document.Document;

import com.graphscape.largegraph.core.EdgeI;
import com.graphscape.largegraph.core.Label;
import com.graphscape.largegraph.core.VertexI;
import com.graphscape.largegraph.core.provider.lucene.document.DocumentFactory;
import com.graphscape.largegraph.core.provider.lucene.document.DocumentType;
import com.graphscape.largegraph.core.provider.lucene.document.operations.GetElementOperation;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class LuceneEdge extends ElementDocument implements EdgeI {

	public static final String PK_LABEL = "label";
	public static final String PK_TAILVERTEXID = "tailVertexId";
	public static final String PK_HEADVERTEXID = "headVertexId";

	public LuceneEdge(DocumentFactory factory, Document doc) {
		super(factory, DocumentType.EDGE, doc);
	}

	@Override
	public Label getLabel() {
		String rtS = (String) this.getProperty(PK_LABEL, true);
		return Label.valueOf(rtS);
	}

	@Override
	public VertexI getTailVertex() {
		String vId = (String) this.getProperty(PK_TAILVERTEXID, true);
		return new GetElementOperation<VertexI>(this.factory,VertexI.class, vId, true).execute();

	}

	@Override
	public VertexI getHeadVertex() {
		String vId = (String) this.getProperty(PK_HEADVERTEXID, true);
		return new GetElementOperation<VertexI>(this.factory,VertexI.class, vId, true).execute();
	}

}
