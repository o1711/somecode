/**
 * Dec 31, 2013
 */
package com.graphscape.largegraph.core.provider.lucene.document.operations;

import java.util.List;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.TermQuery;

import com.graphscape.commons.lang.GsException;
import com.graphscape.largegraph.core.Arrow;
import com.graphscape.largegraph.core.EdgeI;
import com.graphscape.largegraph.core.Label;
import com.graphscape.largegraph.core.provider.lucene.LuceneEdge;
import com.graphscape.largegraph.core.provider.lucene.document.DocumentFactory;
import com.graphscape.largegraph.core.provider.lucene.document.DocumentType;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class GetEdgeListOperation extends GetElementListOperation<EdgeI> {
	protected Label label;

	protected Arrow dir;
	
	protected String vertexId;
	
	public GetEdgeListOperation(DocumentFactory df, Label label, Arrow dir, String vertexId) {
		super(df, EdgeI.class, null);
		this.label = label;
		this.dir = dir;
		this.vertexId = vertexId;
	}

	@Override
	protected List<EdgeI> doExecute() {
		BooleanQuery bq = this.queryByType(DocumentType.EDGE);
		// label
		if (label != null) {
			TermQuery lTq = new TermQuery(new Term(LuceneEdge.PK_LABEL, label.toString()));
			bq.add(lTq, BooleanClause.Occur.MUST);
		}

		// vid
		String vid = this.vertexId;
		if (dir == null || dir.BOTH.equals(dir)) {

			BooleanQuery bq2 = new BooleanQuery();
			bq2.add(new TermQuery(new Term(LuceneEdge.PK_HEADVERTEXID, vid)), BooleanClause.Occur.SHOULD);
			bq2.add(new TermQuery(new Term(LuceneEdge.PK_TAILVERTEXID, vid)), BooleanClause.Occur.SHOULD);
			bq.add(bq2, BooleanClause.Occur.MUST);
		} else if (dir.HEAD.equals(dir)) {
			bq.add(new TermQuery(new Term(LuceneEdge.PK_HEADVERTEXID, vid)), BooleanClause.Occur.MUST);
		} else if (dir.TAIL.equals(dir)) {
			bq.add(new TermQuery(new Term(LuceneEdge.PK_TAILVERTEXID, vid)), BooleanClause.Occur.MUST);
		} else {
			throw new GsException("bug?");
		}
		super.query = bq;//
		return super.doExecute();
	}

}
