/**
 * Dec 14, 2013
 */
package com.graphscape.commons.util;

import com.graphscape.commons.concurrent.FutureI;
import com.graphscape.commons.lang.Wrapper;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class ProxyFuture3<S, T> extends Wrapper<FutureI<S>> implements FutureI<T> {

	public ProxyFuture3(final FutureI<S> f) {
		super(f);
	}

	@Override
	public T get() {
		// TODO Auto-generated method stub
		return (T) this.target.get();
	}

	@Override
	public T get(TimeAndUnit tu) {
		return (T) this.target.get(tu);
	}

}
