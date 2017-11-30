/**
 * Dec 28, 2013
 */
package com.graphscape.largegraph.client.provider.default_.support;

import com.graphscape.commons.lang.CallbackI;
import com.graphscape.commons.message.MessageI;
import com.graphscape.largegraph.client.ClientI;
import com.graphscape.largegraph.client.provider.default_.ClientLargeGraph;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public abstract class MessageCallback<T> implements CallbackI<MessageI, T> {
	protected ClientLargeGraph clg;

	protected ClientI client;

	public MessageCallback(ClientLargeGraph lg) {
		this.clg = lg;
		this.client = lg.getClient();
	}

	@Override
	public T execute(MessageI t) {
		return this.executeInternal(t);
	}

	protected abstract T executeInternal(MessageI t);

}
