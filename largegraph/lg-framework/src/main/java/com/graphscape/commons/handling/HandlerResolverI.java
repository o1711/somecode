/**
 * Dec 8, 2013
 */
package com.graphscape.commons.handling;

import com.graphscape.commons.lang.HandlerI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public interface HandlerResolverI<S, X, T extends HandlingContextI<S, X>> {
	public HandlerI<T> resolve(T t);

}