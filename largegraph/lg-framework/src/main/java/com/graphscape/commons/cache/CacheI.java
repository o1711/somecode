/**
 * Dec 29, 2013
 */
package com.graphscape.commons.cache;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public interface CacheI<T> {

	public CacheElement<T> put(T obj);

	public CacheElement<T> put(Object id, T o);

	public CacheElement<T> getElement(Object id);

	public T get(Object key);

	public void clear();

	public void clear(Object id);
	
	public void clear(Object ... ids );

}
