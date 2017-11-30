/**
 * Jan 20, 2014
 */
package com.graphscape.commons.debug;

import java.util.List;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public interface ProfileI {

	public void start();

	public void end();

	public void beforeExecute(String id);

	public void afterExecute();

	public void dump();

	public void reset();

	public boolean apply(Object obj);
	
	public boolean applyAll(List<Object> childToBeApplied);

	public void maxDepth(int i);


}
