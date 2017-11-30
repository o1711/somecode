/**
 * Jan 5, 2014
 */
package com.graphscape.commons.probject;

import com.graphscape.commons.lang.CallbackI;
import com.graphscape.commons.lang.ResourceI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public interface ProbjectStorageFactoryI extends ResourceI {

	public ProbjectStorageI openTransaction();
	
	public <T> T executeInTransaction(CallbackI<ProbjectStorageI,T> callback);

}
