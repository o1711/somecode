/**
 * Dec 31, 2013
 */
package com.graphscape.largegraph.core.provider.lucene.document;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.BytesRef;

import com.graphscape.commons.lang.PropertiesI;

/**
 * @author wuzhen0808@gmail.com
 *
 */
public abstract class DocumentOperation<T> {
	
	protected Directory directory;
	
	protected DocumentFactory fac;
	
	public DocumentOperation(DocumentFactory df) {
		this.fac = df;
		this.directory = df.getDirectory();		
	}
	
	public abstract T execute();
	
	public Query queryByProperties(DocumentType type, PropertiesI<Object> pts) {
		BooleanQuery rt = this.queryByType(type);
		for (String key : pts.keyList()) {
			Object value = pts.getProperty(key);
			BytesRef bref = type.toBytesRef(value);
			Query fQ = new TermQuery(new Term(key, bref));
			rt.add(fQ, BooleanClause.Occur.MUST);
		}
		return rt;

	}

	public BooleanQuery queryByType(DocumentType type) {
		BooleanQuery bq = new BooleanQuery();
		Query typeQ = new TermQuery(new Term("type", type.getName()));
		bq.add(typeQ, BooleanClause.Occur.MUST);
		return bq;
	}

	public Query queryById(Class cls, String id) {
		return queryById(DocumentType.getType(cls), id);
	}

	public Query queryById(DocumentType type, String id) {
		BooleanQuery bq = this.queryByType(type);
		Query idQ = new TermQuery(new Term("id", id));

		bq.add(idQ, BooleanClause.Occur.MUST);
		return bq;
	}

}
