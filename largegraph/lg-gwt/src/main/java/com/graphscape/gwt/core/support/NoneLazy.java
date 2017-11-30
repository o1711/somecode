/**
 *  Nov 23, 2012
 */
package com.graphscape.gwt.core.support;

import com.graphscape.gwt.core.LazyI;

/**
 * @author wuzhen
 * 
 */
public class NoneLazy<T> implements LazyI<T> {

	protected T object;

	public NoneLazy(T obj) {
		this.object = obj;
	}

	@Override
	public T get() {
		return this.object;
	}

}
