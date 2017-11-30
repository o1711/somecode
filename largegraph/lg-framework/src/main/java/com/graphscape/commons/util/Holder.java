/**
 *  
 */
package com.graphscape.commons.util;

/**
 * @author wu
 * 
 */
public class Holder<T> {

	private T target;

	public T getTarget() {
		return target;
	}

	public void setTarget(T target) {
		this.target = target;
	}

	public Holder() {
		this(null);
	}

	public Holder(T t) {
		this.target = t;
	}

}
