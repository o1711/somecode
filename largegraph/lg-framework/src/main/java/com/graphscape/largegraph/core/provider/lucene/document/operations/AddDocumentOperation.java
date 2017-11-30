/**
 * Dec 31, 2013
 */
package com.graphscape.largegraph.core.provider.lucene.document.operations;

import org.apache.lucene.document.Document;

import com.graphscape.commons.lang.PropertiesI;
import com.graphscape.largegraph.core.ElementI;
import com.graphscape.largegraph.core.provider.lucene.document.DocumentFactory;
import com.graphscape.largegraph.core.provider.lucene.document.DocumentType;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class AddDocumentOperation<T extends ElementI> extends WriteOperation<T> {

	protected DocumentType type;

	protected String id;

	protected PropertiesI<Object> properties;

	/**
	 * @param df
	 */
	public AddDocumentOperation(DocumentFactory df) {
		this(df, null, null);
	}

	public AddDocumentOperation(DocumentFactory df, Class<T> cls, String id) {
		this(df, cls, id, null);
	}

	public AddDocumentOperation(DocumentFactory df, Class<T> cls, String id, PropertiesI<Object> pts) {
		super(df);
		this.type(cls);
		this.id = id;
		this.properties = pts;
	}

	public AddDocumentOperation<T> type(DocumentType type) {
		this.type = type;
		return this;
	}

	public AddDocumentOperation<T> type(Class<T> cls) {
		if (cls == null) {
			type = null;
		} else {
			type = DocumentType.getType(cls);
		}
		return this;
	}

	public AddDocumentOperation<T> id(String id) {
		this.id = id;
		return this;
	}

	public AddDocumentOperation<T> properties(PropertiesI<Object> pts) {
		this.properties = pts;
		return this;
	}

	@Override
	public T doExecute() {

		Document doc = type.buildDocument(id, this.properties);
		this.writer.addDocument(doc);//
		return type.newInstance(this.fac, doc);
	}

}
