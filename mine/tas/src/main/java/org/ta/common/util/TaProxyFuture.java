package org.ta.common.util;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class TaProxyFuture<T> implements Future<T> {
	private Future<T> target;

	public TaProxyFuture(Future<T> target) {
		this.target = target;
	}

	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		//
		return this.target.cancel(mayInterruptIfRunning);

	}

	@Override
	public boolean isCancelled() {

		return this.target.isCancelled();
	}

	@Override
	public boolean isDone() {
		return this.target.isDone();
	}

	@Override
	public T get() throws InterruptedException, ExecutionException {

		return this.target.get();
	}

	@Override
	public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException,
			TimeoutException {

		return this.target.get(timeout, unit);

	}

}
