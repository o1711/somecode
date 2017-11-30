/**
 * Dec 15, 2013
 */
package com.graphscape.largegraph.server.provider.default_;

import com.graphscape.commons.concurrent.FiniteBlockingQueueI;
import com.graphscape.commons.concurrent.provider.LinkedFiniteBlockingQueue;
import com.graphscape.commons.lang.BuilderI;
import com.graphscape.commons.message.MessageI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class DefaultMessageQueueBuilder implements BuilderI<FiniteBlockingQueueI<MessageI>> {

	public DefaultMessageQueueBuilder() {
	}

	@Override
	public FiniteBlockingQueueI<MessageI> build() {

		return new LinkedFiniteBlockingQueue<MessageI>();

	}

}
