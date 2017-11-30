/**
 * Dec 29, 2013
 */
package com.graphscape.commons.cache;

import com.graphscape.commons.lang.Wrapper;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class CacheElement<T> extends Wrapper<T> {

	/**
	 * @param t
	 */
	public CacheElement(T t) {
		super(t);
	}

}
