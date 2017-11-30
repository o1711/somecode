/**
 * Dec 15, 2013
 */
package com.graphscape.largegraph.server.provider.default_;

import com.graphscape.commons.concurrent.FiniteBlockingQueueI;
import com.graphscape.commons.concurrent.provider.LinkedFiniteBlockingQueue;
import com.graphscape.commons.lang.BuilderI;
import com.graphscape.commons.message.MessageI;
import com.graphscape.commons.other.provider.default_.DefaultIdBasedManager;
import com.graphscape.largegraph.server.MessageQueueManagerI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class DefaultMessageQueueManager extends DefaultIdBasedManager<FiniteBlockingQueueI<MessageI>> implements
		MessageQueueManagerI {
	public static class MyBuilder implements BuilderI<FiniteBlockingQueueI<MessageI>> {

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.graphscape.commons.lang.BuilderI#build()
		 */
		@Override
		public FiniteBlockingQueueI<MessageI> build() {
			return new LinkedFiniteBlockingQueue<MessageI>();
		}

	}

	public DefaultMessageQueueManager() {
		this(new MyBuilder());
	}

	/**
	 * @param builder
	 */
	public DefaultMessageQueueManager(BuilderI<FiniteBlockingQueueI<MessageI>> builder) {
		super(builder);
	}

}
