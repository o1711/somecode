package com.graphscape.largegraph.client.provider.default_.handlers;

import com.graphscape.commons.message.MessageI;
import com.graphscape.largegraph.client.support.ClientMessageHandlerSupport;

public class LargeGraphMessageResponseHandler extends ClientMessageHandlerSupport {

	@Override
	protected void handleInternal(MessageI msg) {

		String scode = msg.getHeader("statusCode", true);
		if (scode.equals("ok")) {

		} else {

		}

	}

}
