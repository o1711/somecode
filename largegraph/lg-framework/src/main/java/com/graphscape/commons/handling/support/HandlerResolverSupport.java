/**
 * Dec 30, 2013
 */
package com.graphscape.commons.handling.support;

import com.graphscape.commons.cache.CacheI;
import com.graphscape.commons.cache.provider.DefaultCache;
import com.graphscape.commons.handling.HandlerResolverI;
import com.graphscape.commons.handling.HandlingContextI;
import com.graphscape.commons.lang.HandlerI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public abstract class HandlerResolverSupport<S, X, T extends HandlingContextI<S, X>> implements
		HandlerResolverI<S, X, T> {
	protected HandlerI<T> defaultHandler;
	
	public HandlerResolverSupport() {

	}

	public HandlerResolverSupport(HandlerI<T> def) {
		this.defaultHandler = def;
	}

	@Override
	public HandlerI<T> resolve(T t) {
		HandlerI<T> rt = this.doResolve(t);
		if (rt == null) {
			rt = this.defaultHandler;
		}
		return rt;
	}

	protected abstract HandlerI<T> doResolve(T t);

}
