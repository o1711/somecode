/**
 * Dec 28, 2013
 */
package com.graphscape.commons.util;

import java.util.Iterator;

import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.lang.Wrapper;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public abstract class ProxyIterator<S, T> extends Wrapper<Iterator<S>> implements Iterator<T> {

	public ProxyIterator(Iterator<S> v) {
		super(v);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Iterator#hasNext()
	 */
	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return this.target.hasNext();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Iterator#next()
	 */
	@Override
	public T next() {
		S s = this.target.next();
		T rt = this.convert(s);
		return rt;
	}

	protected abstract T convert(S s);

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Iterator#remove()
	 */
	@Override
	public void remove() {
		throw new GsException("TODO");
	}
}
