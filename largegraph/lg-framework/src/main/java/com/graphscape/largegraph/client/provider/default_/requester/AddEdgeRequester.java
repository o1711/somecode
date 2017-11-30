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
public class AddEdgeRequester extends RequesterSupport<EdgeI> {

	protected String fromId;
	protected String toId;
	protected Label label;
	/**
	 * @param lg
	 * @param client
	 */
	public AddEdgeRequester(String fromId,Label label,String toId,ClientI client) {
		super(client);
		this.fromId = fromId;
		this.label = label;
		this.toId = toId;
	}

	@Override
	public MessageI buildRequest() {
		MessageI m2 = new DefaultMessage(null, Path.valueOf("/lg/add-edge"));
		m2.setPayload("label", label.toString());
		m2.setPayload("startVertexId", fromId);
		m2.setPayload("endVertexId", toId);
		return m2;
	}

	/* (non-Javadoc)
	 * @see com.graphscape.largegraph.client.provider.support.Requester#doConvert(com.graphscape.commons.message.MessageI)
	 */
	@Override
	public EdgeI doConvert(MessageI t) {

		String labelS = (String) t.getPayload("label", true);
		Label label = Label.valueOf(labelS);
		String startId = (String) t.getPayload("startVertexId", true);
		String endId = (String) t.getPayload("endVertexId", true);

		PropertiesI<Object> rt = (PropertiesI) t.getPayload("properties", true);

		String id = (String) t.getPayload("id", true);

		return ClientEdge.valueOf(client, id, label, startId, endId, rt);
	}

}
