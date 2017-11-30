/**
 * Dec 31, 2013
 */
package com.graphscape.largegraph.core.provider.lucene.document.operations;

import com.graphscape.largegraph.core.provider.lucene.document.DocumentFactory;
import com.graphscape.largegraph.core.provider.lucene.document.DocumentOperation;
import com.graphscape.largegraph.core.provider.lucene.document.DocumentWriterI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public abstract class WriteOperation<T> extends DocumentOperation<T> {
	DocumentWriterI writer;

	/**
	 * @param df
	 * @param directory
	 */
	public WriteOperation(DocumentFactory df) {
		super(df);
		this.writer = this.fac.writer();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.largegraph.core.provider.lucene.document.DocumentOperation
	 * #execute()
	 */
	@Override
	public T execute() {
		T rt = this.doExecute();
		this.writer.commit();
		return rt;
	}

	protected abstract T doExecute();

}
