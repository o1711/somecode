/**
 * Dec 29, 2013
 */
package com.graphscape.commons.cache.provider;

import java.util.HashMap;
import java.util.Map;

import com.graphscape.commons.cache.CacheElement;
import com.graphscape.commons.cache.CacheI;
import com.graphscape.commons.debug.support.ProfileAwareSupport;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.lang.HasIdI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class DefaultCache<T> extends ProfileAwareSupport implements CacheI<T> {

	private static final String PROFILE_PUT = DefaultCache.class.getName() + ".put";
	private static final String PROFILE_GET = DefaultCache.class.getName() + ".get";
	private static final String PROFILE_GETELEMENT = DefaultCache.class.getName() + ".getElement";

	private Map<Object, CacheElement<T>> map = new HashMap<Object, CacheElement<T>>();

	@Override
	public CacheElement<T> put(Object key, T o) {
		this.beforeProfile(PROFILE_PUT);
		try {

			CacheElement<T> rt = new CacheElement<T>(o);
			this.map.put(key, rt);
			return rt;
		} finally {
			this.afterProfile();
		}

	}

	@Override
	public T get(Object key) {
		CacheElement<T> e = this.getElement(key);
		if (e == null) {
			return null;
		}
		return e.getTarget();
	}

	@Override
	public CacheElement<T> getElement(Object key) {
		this.beforeProfile(PROFILE_GETELEMENT);
		try {

			CacheElement<T> rt = this.map.get(key);
			return rt;
		} finally {
			this.afterProfile();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.cache.CacheI#put(java.lang.Object)
	 */
	@Override
	public CacheElement<T> put(T obj) {
		if (obj instanceof HasIdI) {
			Object id = ((HasIdI) obj).getId();
			return this.put(id, obj);
		}
		throw new GsException("cannot cache obj:" + obj);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.cache.CacheI#clear()
	 */
	@Override
	public void clear() {
		this.map.clear();
	}

	@Override
	public void clear(Object id) {
		this.map.remove(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.cache.CacheI#clear(java.lang.Object[])
	 */
	@Override
	public void clear(Object... ids) {
		for (Object id : ids) {
			this.clear(id);//
		}
	}

}
