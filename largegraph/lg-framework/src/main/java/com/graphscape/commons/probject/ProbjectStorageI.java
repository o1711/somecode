/**
 * Jan 22, 2014
 */
package com.graphscape.commons.probject;

import com.graphscape.commons.lang.PropertiesI;
import com.graphscape.commons.lang.TransactionI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public interface ProbjectStorageI extends TransactionI {

	public ProbjectI add(String id);

	public ProbjectI add(String id, PropertiesI<Object> pts);

	public ProbjectI get(String id);
	
	public void remove(String id);

}
