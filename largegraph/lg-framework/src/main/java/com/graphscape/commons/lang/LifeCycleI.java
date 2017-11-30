/**
 * Dec 9, 2013
 */
package com.graphscape.commons.lang;

import java.util.concurrent.Future;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public interface LifeCycleI {

	public void start();

	/**
	 * The future when this life cycle shutdown.
	 * 
	 * @return
	 */
	public Future<LifeCycleI> join();

	public void shutdown();

}
