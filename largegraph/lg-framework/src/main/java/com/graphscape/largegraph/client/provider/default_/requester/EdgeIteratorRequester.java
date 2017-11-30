/**
 * Dec 29, 2013
 */
package com.graphscape.largegraph.client.provider.default_.requester;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
public class EdgeIteratorRequester extends RequesterSupport<Iterator<EdgeI>> {

	protected String vertexId;
	protected Label label;

	/**
	 * @param client
	 */
	public EdgeIteratorRequester(String vid, Label label, ClientI client) {
		super(client);
		this.vertexId = vid;
		this.label = label;
	}

	@Override
	public MessageI buildRequest() {
		MessageI m2 = new DefaultMessage(null, Path.valueOf("/lg/get-edge-iterator"));
		m2.setPayload("vertexId", vertexId);
		m2.setPayload("label", label.toString());

		return m2;
	}

	@Override
	public Iterator<EdgeI> doConvert(MessageI t) {
		List<PropertiesI<Object>> l = (List<PropertiesI<Object>>) t.getPayload("list");
		List<EdgeI> vL = new ArrayList<EdgeI>();
		for (PropertiesI<Object> e : l) {

			String id = (String) e.getProperty("id", true);
			String labelS = (String) e.getProperty("label", true);
			String fId = (String) e.getProperty("startVertexId", true);
			String tId = (String) e.getProperty("endVertexId", true);
			Label label = Label.valueOf(labelS);
			PropertiesI<Object> ptsI = (PropertiesI) e.getProperty("properties", true);

			EdgeI vI = ClientEdge.valueOf(client, id, label, fId, tId, ptsI);
			vL.add(vI);
		}

		return vL.iterator();

	}

}
