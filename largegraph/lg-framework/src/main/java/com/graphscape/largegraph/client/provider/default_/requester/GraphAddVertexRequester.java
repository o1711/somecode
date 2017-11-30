/**
 * Dec 29, 2013
 */
package com.graphscape.largegraph.client.provider.default_.requester;

import com.graphscape.commons.message.MessageI;
import com.graphscape.commons.message.provider.default_.DefaultMessage;
import com.graphscape.commons.util.Path;
import com.graphscape.largegraph.client.ClientI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class GraphAddVertexRequester extends GraphRequester<Void> {

	protected String vertexId;

	/**
	 * @param client
	 */
	public GraphAddVertexRequester(String gid, String vid, ClientI client) {
		super(gid, client);
		this.vertexId = vid;
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
		MessageI m2 = new DefaultMessage(null, Path.valueOf("/lg/graph-add-vertex"));
		m2.setPayload("graphId", graphId);
		m2.setPayload("vertexId", vertexId);
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
	public Void doConvert(MessageI t) {

		return null;
	}

}
