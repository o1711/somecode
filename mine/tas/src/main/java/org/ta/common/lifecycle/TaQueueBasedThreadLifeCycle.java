package org.ta.common.lifecycle;

import java.util.concurrent.BlockingQueue;

import org.ta.common.util.TaConsumer;

public class TaQueueBasedThreadLifeCycle<E, T> extends TaBaseConsumerThreadLifeCycle<E, T> {
	protected BlockingQueue<E> eventQueue;

	public TaQueueBasedThreadLifeCycle(BlockingQueue<E> eventQueue, T result,
			TaConsumer<E> c) {
		super(result, c);
		this.eventQueue = eventQueue;
	}

	@Override
	protected void doRun() {

		while (true) {
			try {
				E evt = this.eventQueue.take();

				boolean stop = this.visit(evt);

				if (stop) {
					break;
				}
			} catch (InterruptedException e) {
				continue;
			}

		}

	}

}
