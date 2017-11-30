/**
 * Dec 31, 2013
 */
package com.graphscape.largegraph.core.provider.lucene.document.operations;

import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.Query;

import com.graphscape.commons.lang.GsException;
import com.graphscape.largegraph.core.provider.lucene.document.DocumentFactory;
import com.graphscape.largegraph.core.provider.lucene.document.DocumentType;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class GetDocumentOperation extends ReadOperation<Document> {

	DocumentType type;
	String id;

	/**
	 * @param df
	 */
	public GetDocumentOperation(DocumentFactory df, DocumentType type, String id) {
		super(df);
		this.type = type;
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.largegraph.core.provider.lucene.document.operations.
	 * ReadOperation#doExecute()
	 */
	@Override
	protected Document doExecute() {
		if (id == null) {
			throw new IllegalArgumentException("id is null");
		}

		Query bq = this.queryById(type, id);
		List<Document> rt = this.reader.getDocumentList(bq);
		if (rt.isEmpty()) {
			return null;
		} else if (rt.size() == 1) {
			return rt.get(0);
		} else {
			throw new GsException("to much");
		}
	}

}
