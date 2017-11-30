/**
 * Jan 5, 2014
 */
package com.graphscape.commons.record;

import com.graphscape.commons.lang.ResourceI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public interface TxStorageFactoryI extends ResourceI {

	public TxStorageI openTransaction();

	public TxStorageI openTransaction(TxListenerI lis);

}
