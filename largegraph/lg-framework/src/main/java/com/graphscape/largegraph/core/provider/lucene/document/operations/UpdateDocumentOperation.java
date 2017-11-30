/**
 * Dec 31, 2013
 */
package com.graphscape.largegraph.core.provider.lucene.document.operations;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.Query;

import com.graphscape.commons.lang.PropertiesI;
import com.graphscape.largegraph.core.ElementI;
import com.graphscape.largegraph.core.provider.lucene.document.DocumentFactory;
import com.graphscape.largegraph.core.provider.lucene.document.DocumentType;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class UpdateDocumentOperation extends WriteOperation<Void> {

	protected DocumentType type;

	protected String id;

	protected Document doc;

	/**
	 * @param df
	 */

	public UpdateDocumentOperation(DocumentFactory df, DocumentType type, String id, Document doc) {
		super(df);
		this.type = type;
		this.id = id;
		this.doc = doc;
	}

	public UpdateDocumentOperation type(DocumentType type) {
		this.type = type;
		return this;
	}

	public UpdateDocumentOperation type(Class cls) {
		if (cls == null) {
			type = null;
		} else {
			type = DocumentType.getType(cls);
		}
		return this;
	}

	public UpdateDocumentOperation id(String id) {
		this.id = id;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.largegraph.core.provider.lucene.document.operations.
	 * WriteOperation#doExecute()
	 */
	@Override
	protected Void doExecute() {

		Query query = this.queryById(type, id);
		writer.deleteDocuments(query);
		writer.addDocument(doc);
		return null;
	}

}
