/**
 * Dec 31, 2013
 */
package com.graphscape.largegraph.core.provider.lucene.document.operations;

import com.graphscape.largegraph.core.provider.lucene.document.DocumentFactory;
import com.graphscape.largegraph.core.provider.lucene.document.DocumentOperation;
import com.graphscape.largegraph.core.provider.lucene.document.DocumentReaderI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public abstract class ReadOperation<T> extends DocumentOperation<T> {
	DocumentReaderI reader;

	/**
	 * @param df
	 * @param directory
	 */
	public ReadOperation(DocumentFactory df) {
		super(df);
		this.reader = this.fac.reader();
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
		return rt;
	}

	protected abstract T doExecute();

}
