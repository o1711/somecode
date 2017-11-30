package com.graphscape.commons.lang.support;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.graphscape.commons.concurrent.FutureI;
import com.graphscape.commons.concurrent.provider.DefaultFuture;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.lang.WorkerI;

public abstract class ThreadWorkerSupport<T> implements WorkerI<T> {

	protected ExecutorService executor = Executors.newSingleThreadScheduledExecutor();

	protected FutureI<T> future;

	protected boolean sumited;

	@Override
	public FutureI<T> submit() {
		Future<T> f = this.executor.submit(new Callable<T>() {

			@Override
			public T call() throws Exception {
				return ThreadWorkerSupport.this.call();
			}
		});
		this.sumited = true;
		this.future = DefaultFuture.valueOf(f);
		return this.future;
	}

	public void assertSubmited() {
		if (!this.sumited) {
			throw new GsException("not submited:" + this);
		}

	}

	public void submitIfNotYet() {
		if (this.sumited) {
			return;
		}
		this.submit();
	}

	protected abstract T call();
}
