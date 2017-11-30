package com.graphscape.largegraph.client.support;

import com.graphscape.commons.lang.HandlerI;
import com.graphscape.commons.message.MessageI;

public abstract class ClientMessageHandlerSupport implements HandlerI<MessageI> {

	@Override
	public void handle(MessageI t) {
		this.handleInternal(t);
	}

	protected abstract void handleInternal(MessageI t);

}
