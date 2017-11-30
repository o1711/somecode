/**
 * Jan 6, 2014
 */
package com.graphscape.commons.record;

import com.graphscape.commons.lang.TransactionI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public interface TxIndexI<K, V> extends IndexI<K, V>, TransactionI {

}
