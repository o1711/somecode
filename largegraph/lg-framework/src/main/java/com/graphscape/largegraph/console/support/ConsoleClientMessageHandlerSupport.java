package com.graphscape.largegraph.console.support;

import com.graphscape.commons.lang.HandlerI;
import com.graphscape.commons.message.MessageI;

public abstract class ConsoleClientMessageHandlerSupport implements HandlerI<MessageI> {

	@Override
	public void handle(MessageI t) {
		this.handleInternal(t);
	}

	public abstract void handleInternal(MessageI t);

}
