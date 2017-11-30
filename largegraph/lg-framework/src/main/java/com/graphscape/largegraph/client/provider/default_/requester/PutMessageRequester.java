/**
 * Dec 28, 2013
 */
package com.graphscape.largegraph.client.provider.default_.requester;

import com.graphscape.commons.lang.PropertiesI;
import com.graphscape.commons.message.MessageI;
import com.graphscape.commons.message.provider.default_.DefaultMessage;
import com.graphscape.commons.util.Path;
import com.graphscape.largegraph.client.ClientI;
import com.graphscape.largegraph.client.provider.default_.ClientEdge;
import com.graphscape.largegraph.client.provider.support.RequesterSupport;
import com.graphscape.largegraph.core.EdgeI;
import com.graphscape.largegraph.core.Label;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class PutMessageRequester extends RequesterSupport<MessageI> {

	protected MessageI msg;

	/**
	 * @param lg
	 * @param client
	 */
	public PutMessageRequester(MessageI msg, ClientI client) {
		super(client);
		this.msg = msg;
	}

	@Override
	public MessageI buildRequest() {
		MessageI m2 = new DefaultMessage(null, Path.valueOf("/lg/put-message"));
		m2.setPayload("message", msg);

		return m2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.largegraph.client.provider.support.Requester#doConvert
	 * (com.graphscape.commons.message.MessageI)
	 */
	@Override
	public MessageI doConvert(MessageI t) {
		MessageI rt = (MessageI) t.getPayload("message", true);
		return rt;
	}

}
