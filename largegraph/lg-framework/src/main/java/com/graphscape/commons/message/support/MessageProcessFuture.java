/**
 * Dec 8, 2013
 */
package com.graphscape.commons.message.support;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.message.MessageI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class MessageProcessFuture implements Future<MessageI> {

	private Future<MessageI> target;

	private Semaphore submited = new Semaphore(0);

	public MessageProcessFuture() {
		
	}

	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		return getTargetFuture().cancel(mayInterruptIfRunning);

	}

	private Future<MessageI> getTargetFuture() {
		if (this.target != null) {
			return this.target;
		}
		try {
			// NOTE, if the processor not started,here will be a dead lock.
			this.submited.acquire();
		} catch (InterruptedException e) {
			throw new GsException(e);
		}
		return this.target;
	}

	@Override
	public boolean isCancelled() {

		return getTargetFuture().isCancelled();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.concurrent.Future#isDone()
	 */
	@Override
	public boolean isDone() {

		return getTargetFuture().isDone();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.concurrent.Future#get()
	 */
	@Override
	public MessageI get() throws InterruptedException, ExecutionException {

		return getTargetFuture().get();
	}

	@Override
	public MessageI get(long timeout, TimeUnit unit)
			throws InterruptedException, ExecutionException, TimeoutException {
		return getTargetFuture().get(timeout, unit);
	}

	/**
	 * @param f
	 */
	public void setTarget(Future<MessageI> f) {
		this.target = f;
		this.submited.release();
	}

}