/**
 * Dec 8, 2013
 */
package com.graphscape.commons.lang;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class Wrapper<T> {

	protected T target;

	/**
	 * @return the target
	 */
	public T getTarget() {
		return target;
	}

	public void setTarget(T t) {
		this.target = t;
	}

	public Wrapper(T t) {
		this.target = t;
	}

}
