/**
 * Dec 29, 2013
 */
package com.graphscape.largegraph.client.provider.default_.requester;

import com.graphscape.commons.lang.ErrorInfos;
import com.graphscape.commons.lang.PropertiesI;
import com.graphscape.commons.message.MessageI;
import com.graphscape.commons.message.provider.default_.DefaultMessage;
import com.graphscape.commons.util.Path;
import com.graphscape.largegraph.client.ClientI;
import com.graphscape.largegraph.client.provider.default_.ClientVertex;
import com.graphscape.largegraph.client.provider.support.RequesterSupport;
import com.graphscape.largegraph.core.VertexI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class AddVertexRequester extends RequesterSupport<VertexI> {

	protected PropertiesI<Object> pts;

	/**
	 * @param client
	 */
	public AddVertexRequester(PropertiesI<Object> pts, ClientI client) {
		super(client);
		this.pts = pts;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.largegraph.client.provider.support.Requester#buildRequest
	 * ()
	 */
	@Override
	public MessageI buildRequest() {
		MessageI m2 = new DefaultMessage(null, Path.valueOf("/lg/add-vertex"));
		m2.setPayload("properties", pts);

		return m2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.largegraph.client.provider.support.Requester#convert(com
	 * .graphscape.commons.message.MessageI)
	 */
	@Override
	public VertexI doConvert(MessageI t) {

		PropertiesI<Object> rt = (PropertiesI) t.getPayload("properties", true);
		String id = (String) t.getPayload("id", true);

		return ClientVertex.valueOf(client, id, rt);
	}

}
