/**
 * Dec 31, 2013
 */
package com.graphscape.largegraph.core.provider.lucene.document.operations;

import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.Query;

import com.graphscape.commons.lang.GsException;
import com.graphscape.largegraph.core.ElementI;
import com.graphscape.largegraph.core.provider.lucene.document.DocumentFactory;
import com.graphscape.largegraph.core.provider.lucene.document.DocumentType;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class GetElementOperation<T extends ElementI> extends ReadOperation<T> {

	DocumentType type;
	String id;
	boolean force;

	/**
	 * @param df
	 */
	public GetElementOperation(DocumentFactory df, Class<T> cls, String id) {
		this(df,cls,id,false);
	}
	public GetElementOperation(DocumentFactory df, Class<T> cls, String id,boolean force) {
		super(df);
		this.type = DocumentType.getType(cls);
		this.id = id;
		this.force = force;
	}
	public GetElementOperation(DocumentFactory df, DocumentType type, String id) {
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
	protected T doExecute() {
		Document doc = new GetDocumentOperation(this.fac,type, id).execute();
		if (doc == null) {
			if (force) {
				throw new GsException("not found element:" + this.type + ",id:" + id);
			}
			return null;
		}
		T rt =  type.newInstance(this.fac, doc);

		return rt;
	}

}
