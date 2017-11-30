/**
 * Dec 31, 2013
 */
package com.graphscape.largegraph.core.provider.lucene.document.operations;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;

import com.graphscape.commons.lang.PropertiesI;
import com.graphscape.largegraph.core.ElementI;
import com.graphscape.largegraph.core.provider.lucene.document.DocumentFactory;
import com.graphscape.largegraph.core.provider.lucene.document.DocumentType;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class GetElementListOperation<T extends ElementI> extends ReadOperation<List<T>> {

	DocumentType type;
	PropertiesI<Object> properties;

	BooleanQuery query;

	/**
	 * @param df
	 */
	public GetElementListOperation(DocumentFactory df, Class<T> cls, PropertiesI<Object> pts) {
		super(df);
		this.type = DocumentType.getType(cls);
		this.properties = pts;
	}

	public GetElementListOperation(DocumentFactory df, DocumentType type, BooleanQuery q) {
		super(df);
		this.type = type;
		this.query = q;
	}

	public GetElementListOperation(DocumentFactory df, DocumentType type, PropertiesI<Object> pts) {
		super(df);
		this.type = type;
		this.properties = pts;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.largegraph.core.provider.lucene.document.operations.
	 * ReadOperation#doExecute()
	 */
	@Override
	protected List<T> doExecute() {

		Query q = this.query;
		if (q == null) {
			q = this.queryByProperties(type, this.properties);
		}
		List<Document> docL = this.reader.getDocumentList(q);
		List<T> rt = new ArrayList<T>();
		for (Document doc : docL) {
			rt.add((T) type.newInstance(fac, doc));
		}
		return rt;
	}

}
