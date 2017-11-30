/**
 * Dec 14, 2013
 */
package com.graphscape.commons.util;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.graphscape.commons.lang.CallbackI;

/**
 * @author wuzhen0808@gmail.com
 * @deprecated
 */
public class ProxyFuture2<S, T> extends ProxyFuture<S, T> implements Future<T> {

	private CallbackI<S, T> object;

	public ProxyFuture2(Future<S> f, CallbackI<S, T> obj) {
		super(f);
		this.object = obj;
	}

	public ProxyFuture2(Future<S> f, final T obj) {
		this(f, new CallbackI<S, T>() {

			@Override
			public T execute(S s) {
				return obj;
			}
		});
	}

	@Override
	public T get() throws InterruptedException, ExecutionException {
		// TODO Auto-generated method stub
		S s = this.target.get();
		return this.getTargetObject(s);
	}

	private T getTargetObject(S obj) throws ExecutionException {
		try {
			return this.object.execute(obj);
		} catch (Exception e) {
			throw new ExecutionException(e);
		}

	}

	@Override
	public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException,
			TimeoutException {
		S s = this.target.get(timeout, unit);
		return this.getTargetObject(s);
	}

}
