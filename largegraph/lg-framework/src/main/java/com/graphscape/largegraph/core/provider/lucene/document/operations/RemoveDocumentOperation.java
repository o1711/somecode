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
public class RemoveDocumentOperation extends WriteOperation<Void> {

	protected DocumentType type;

	protected String id;

	protected PropertiesI<Object> properties;

	/**
	 * @param df
	 */

	public RemoveDocumentOperation(DocumentFactory df, DocumentType type, String id) {
		super(df);
		this.type = type;
		this.id = id;
	}

	public RemoveDocumentOperation(DocumentFactory df, DocumentType type, PropertiesI<Object> pts) {
		super(df);
		this.type = type;
		this.properties = pts;
	}

	public RemoveDocumentOperation type(DocumentType type) {
		this.type = type;
		return this;
	}

	public RemoveDocumentOperation type(Class cls) {
		if (cls == null) {
			type = null;
		} else {
			type = DocumentType.getType(cls);
		}
		return this;
	}

	public RemoveDocumentOperation id(String id) {
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
		if (this.id != null) {
			Query query = this.queryById(type, id);
			writer.deleteDocuments(query);

		} else if (this.properties != null) {
			Query query = this.queryByProperties(type, this.properties);
			writer.deleteDocuments(query);

		}
		return null;
	}

}
