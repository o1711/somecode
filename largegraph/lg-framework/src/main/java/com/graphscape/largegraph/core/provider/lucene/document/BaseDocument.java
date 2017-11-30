/**
 * Dec 31, 2013
 */
package com.graphscape.largegraph.core.provider.lucene.document;

import org.apache.lucene.document.Document;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class BaseDocument {
	protected DocumentType documentType;
	protected Document target;
	protected DocumentFactory factory;

	public BaseDocument(DocumentFactory factory, DocumentType type, Document t) {
		this.target = t;
		this.factory = factory;
		this.documentType = type;
	}

}
