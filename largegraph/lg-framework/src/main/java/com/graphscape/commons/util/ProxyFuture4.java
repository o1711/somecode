/**
 * Dec 14, 2013
 */
package com.graphscape.commons.util;

import com.graphscape.commons.concurrent.FutureI;
import com.graphscape.commons.lang.CallbackI;
import com.graphscape.commons.lang.GsException;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class ProxyFuture4<S, T> extends ProxyFuture3<S, T> implements FutureI<T> {

	private CallbackI<S, T> object;

	public ProxyFuture4(FutureI<S> f, CallbackI<S, T> obj) {
		super(f);
		this.object = obj;
	}

	public ProxyFuture4(FutureI<S> f, final T obj) {
		this(f, new CallbackI<S, T>() {

			@Override
			public T execute(S s) {
				return obj;
			}
		});
	}

	@Override
	public T get() {
		// TODO Auto-generated method stub
		S s = this.target.get();
		return this.getTargetObject(s);
	}

	private T getTargetObject(S obj) {
		try {
			return this.object.execute(obj);
		} catch (Exception e) {
			throw GsException.toRuntimeException(e);
		}

	}

	@Override
	public T get(TimeAndUnit tu) {
		S s = this.target.get(tu);
		return this.getTargetObject(s);
	}

}
