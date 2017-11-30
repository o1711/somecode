/**
 * Jan 5, 2014
 */
package com.graphscape.commons.record.provider.storage;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.graphscape.commons.file.FileManagerI;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.lang.Wrapper;
import com.graphscape.commons.record.StorageI;
import com.graphscape.commons.record.TxStorageI;
import com.graphscape.commons.record.TxListenerI;
import com.graphscape.commons.record.TxStorageFactoryI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class DefaultTxStorageManager extends Wrapper<StorageI> implements TxStorageFactoryI {

	private Lock transactionLock = new ReentrantLock();

	public DefaultTxStorageManager(FileManagerI fm) {
		this(new DefaultStorage(fm));
	}

	/**
	 * @param t
	 */
	public DefaultTxStorageManager(StorageI t) {
		super(t);

	}

	@Override
	public TxStorageI openTransaction() {
		return this.doOpenTransaction(null);
	}

	@Override
	public TxStorageI openTransaction(TxListenerI lis) {
		return this.doOpenTransaction(lis);
	}

	private TxStorageI doOpenTransaction(TxListenerI lis) {
		DefaultTxStorage rt = new DefaultTxStorage(this, this.transactionLock, lis);
		rt.open();
		return rt;
	}

	@Override
	public void open() {
		this.target.open();
	}

	@Override
	public void close() {
		this.target.close();
	}

}
