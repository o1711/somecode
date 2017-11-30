/**
 * Dec 14, 2013
 */
package com.graphscape.commons.util;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.graphscape.commons.lang.Wrapper;

/**
 * @author wuzhen0808@gmail.com
 * @deprecated
 */
public class ProxyFuture<S, T> extends Wrapper<Future<S>> implements Future<T> {

	public ProxyFuture(final Future<S> f) {
		super(f);
	}

	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		return this.target.cancel(mayInterruptIfRunning);
	}

	@Override
	public boolean isCancelled() {
		return this.target.isCancelled();
	}

	@Override
	public boolean isDone() {
		// TODO Auto-generated method stub
		return this.target.isDone();
	}

	@Override
	public T get() throws InterruptedException, ExecutionException {
		// TODO Auto-generated method stub
		return (T) this.target.get();
	}

	@Override
	public T get(long timeout, TimeUnit unit) throws InterruptedException,
			ExecutionException, TimeoutException {
		return (T) this.target.get(timeout, unit);
	}

}
