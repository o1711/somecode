package org.ta.common.lifecycle;

import org.slf4j.LoggerFactory;
import org.ta.common.util.TaConsumer;

public abstract class TaBaseConsumerThreadLifeCycle<E, T> extends TaSimpleThreadLifeCycle
		implements TaLifeCycle {

	protected T result;

	protected String name;

	protected TaConsumer<E> visitor;

	public TaBaseConsumerThreadLifeCycle(T result, TaConsumer<E> consumer) {
		this.result = result;
		this.visitor = consumer;
		this.log = LoggerFactory.getLogger(this.getClass());
		this.name = this.getClass().getName();
	}

	public boolean visit(E evt) {
		if (this.visitor != null) {
			return this.visitor.visit(evt);
		}
		return false;
	}

	@Override
	public void stop() {
		if (log.isInfoEnabled()) {
			log.info("stop:");
		}
		this.stopRequired = true;
	}

}
