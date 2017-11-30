/**
 * Dec 22, 2013
 */
package com.graphscape.commons.concurrent;

import com.graphscape.commons.util.TimeAndUnit;

/**
 * @author wuzhen0808@gmail.com
 *
 */
public interface FutureI<T> {
	public T get();
	public T get(TimeAndUnit tu);
	
}
