package org.ta.common.lifecycle;

import java.util.Iterator;

import org.ta.common.util.TaConsumer;

public class TaIteratorBasedThreadLifeCycle<E, T> extends TaBaseConsumerThreadLifeCycle<E, T> {
	protected Iterator<E> it;

	public TaIteratorBasedThreadLifeCycle(Iterator<E> eventQueue, T result,
			TaConsumer<E> consumer) {
		super(result, consumer);
		this.it = eventQueue;
	}

	@Override
	protected void doRun() {

		while (it.hasNext()) {
			E evt = this.it.next();
			boolean stop = this.visit(evt);
			if (stop) {
				break;
			}
		}

	}

}
