/**
 *  
 */
package com.graphscape.commons.handling.provider.default_;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.graphscape.commons.concurrent.FutureI;
import com.graphscape.commons.concurrent.provider.DefaultFuture;
import com.graphscape.commons.handling.HandlingContextI;
import com.graphscape.commons.handling.ProcessorI;
import com.graphscape.commons.handling.spi.HandlingProviderI;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.lang.support.LifeCycleSupport;

/**
 * @author wu
 * 
 */
public class DefaultMessageProcessor<S, T, C extends HandlingContextI<S, T>> extends LifeCycleSupport
		implements ProcessorI<S, T> {

	private static Logger LOG = LoggerFactory.getLogger(DefaultMessageProcessor.class);

	private HandlingProviderI<S, T, C> mspi;

	private ExecutorService executor;

	public DefaultMessageProcessor(HandlingProviderI<S, T, C> mspi) {
		this.mspi = mspi;
		// when size is >10 and <MAX, and the queue is full, new thread for new
		// task.
		this.executor = new ThreadPoolExecutor(10, Integer.MAX_VALUE, 1L, TimeUnit.MINUTES,
				new SynchronousQueue<Runnable>());
	}

	@Override
	public void doStart() {

	}

	@Override
	public void doShutdown() {

		this.executor.shutdown();
		try {
			this.executor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			throw new GsException(e);
		}

	}

	@Override
	public FutureI<T> process(S msg) {
		this.assertIsRunning();
		Future<T> f = this.executor.submit(new ProcessTask<S, T, C>(this.mspi, msg));
		return DefaultFuture.valueOf(f);
	}

}
