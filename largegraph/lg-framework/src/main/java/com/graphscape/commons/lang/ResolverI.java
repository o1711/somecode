/**
 * Dec 17, 2013
 */
package com.graphscape.commons.lang;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public interface ResolverI<S, T> {
	public T resolve(S s);
}
