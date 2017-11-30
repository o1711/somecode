package org.ta.common.lifecycle;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ta.common.config.TaException;

public abstract class TaSimpleThreadLifeCycle implements TaThreadLifeCycle {

	protected Logger log;

	protected Thread thread;

	protected FutureTask<TaSimpleThreadLifeCycle> future;

	protected boolean running;

	protected boolean stopRequired;

	protected String name;

	public TaSimpleThreadLifeCycle() {
		this.log = LoggerFactory.getLogger(this.getClass());
		this.name = this.getClass().getName();
	}

	@Override
	public void start() {
		if (log.isInfoEnabled()) {
			log.info("start");
		}

		if (this.future != null) {
			throw new TaException("already started.");
		}
		this.future = new FutureTask<TaSimpleThreadLifeCycle>(
				new Callable<TaSimpleThreadLifeCycle>() {

					@Override
					public TaSimpleThreadLifeCycle call() throws Exception {
						TaSimpleThreadLifeCycle.this.run();
						return TaSimpleThreadLifeCycle.this;
					}
				});
		this.thread = new Thread(this.future);
		this.thread.start();
		this.running = true;

	}

	private void run() {
		try {

			if (log.isInfoEnabled()) {
				log.info("before running.");
			}

			this.doRun();

			this.running = false;
			if (log.isInfoEnabled()) {
				log.info("after running,");
			}
		} catch (Throwable t) {
			this.onException(t);//
		}

	}

	protected abstract void doRun();

	protected void onException(Throwable t) {
		log.error("", t);//
	}

	@Override
	public void join() {
		try {
			this.future.get();
		} catch (InterruptedException e) {
			throw new TaException(e);
		} catch (ExecutionException e) {
			throw new TaException(e.getCause());
		}
	}

	@Override
	public void stop() {
		if (log.isInfoEnabled()) {
			log.info("stop:");
		}
		this.stopRequired = true;
	}

}
