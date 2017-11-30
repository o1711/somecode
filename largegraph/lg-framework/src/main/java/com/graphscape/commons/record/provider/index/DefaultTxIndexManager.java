/**
 * Jan 6, 2014
 */
package com.graphscape.commons.record.provider.index;

import com.graphscape.commons.file.FileManagerI;
import com.graphscape.commons.record.TxIndexI;
import com.graphscape.commons.record.TxIndexManagerI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class DefaultTxIndexManager implements TxIndexManagerI {
	private FileManagerI fileManager;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.lang.ResourceI#open()
	 */
	@Override
	public void open() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.lang.ResourceI#close()
	 */
	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.commons.record.TxIndexManagerI#openTransactionIndex(java
	 * .lang.String)
	 */
	@Override
	public <K, V> TxIndexI<K, V> openTransactionIndex(String index) {
		return null;//
	}

}
