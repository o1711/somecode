/**
 * Jan 6, 2014
 */
package com.graphscape.commons.record;

import com.graphscape.commons.lang.ResourceI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public interface TxIndexManagerI extends ResourceI {

	public <K, V> TxIndexI<K, V> openTransactionIndex(String index);

}
