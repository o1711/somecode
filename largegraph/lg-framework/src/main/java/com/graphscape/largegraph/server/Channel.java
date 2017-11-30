/**
 * Dec 16, 2013
 */
package com.graphscape.largegraph.server;

import com.graphscape.commons.concurrent.FiniteBlockingQueueI;
import com.graphscape.commons.message.MessageI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class Channel {

	FiniteBlockingQueueI<MessageI> in;

	FiniteBlockingQueueI<MessageI> out;

	public Channel(FiniteBlockingQueueI<MessageI> in, FiniteBlockingQueueI<MessageI> out) {
		this.in = in;
		this.out = out;
	}

	public FiniteBlockingQueueI<MessageI> getIn() {
		return in;
	}

	public FiniteBlockingQueueI<MessageI> getOut() {
		return out;
	}

	/**
	 * @param msg
	 */
	public void write(MessageI msg) {
		this.out.put(msg);
	}

}
