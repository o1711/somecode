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
import com.graphscape.largegraph.client.provider.default_.ClientVertex;
import com.graphscape.largegraph.client.provider.support.RequesterSupport;
import com.graphscape.largegraph.core.VertexI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class VertexIteratorRequester extends RequesterSupport<Iterator<VertexI>> {

	protected String graphId;

	/**
	 * @param client
	 */
	public VertexIteratorRequester(String gid, ClientI client) {
		super(client);
		this.graphId = gid;
	}

	@Override
	public MessageI buildRequest() {
		MessageI m2 = new DefaultMessage(null, Path.valueOf("/lg/get-vertex-iterator"));
		m2.setPayload("graphId", graphId);
		return m2;
	}

	@Override
	public Iterator<VertexI> doConvert(MessageI t) {
		List<PropertiesI<Object>> l = (List<PropertiesI<Object>>) t.getPayload("list");
		List<VertexI> vL = new ArrayList<VertexI>();
		for (PropertiesI<Object> e : l) {

			String id = (String) e.getProperty("id", true);
			PropertiesI<Object> ptsI = (PropertiesI) e.getProperty("properties", true);
			VertexI vI = ClientVertex.valueOf(client, id, ptsI);
			vL.add(vI);
		}

		return vL.iterator();

	}

}
