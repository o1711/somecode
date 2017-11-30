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
import com.graphscape.largegraph.client.provider.default_.ClientGraph;
import com.graphscape.largegraph.client.provider.support.RequesterSupport;
import com.graphscape.largegraph.core.GraphI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class GraphIteratorRequester extends RequesterSupport<Iterator<GraphI>> {

	protected String vertexId;

	/**
	 * @param client
	 */
	public GraphIteratorRequester(String vid, ClientI client) {
		super(client);
		this.vertexId = vid;
	}

	@Override
	public MessageI buildRequest() {
		MessageI m2 = new DefaultMessage(null, Path.valueOf("/lg/get-graph-iterator"));
		m2.setPayload("vertexId", vertexId);
		return m2;
	}

	@Override
	public Iterator<GraphI> doConvert(MessageI t) {
		List<PropertiesI<Object>> l = (List<PropertiesI<Object>>) t.getPayload("list");
		List<GraphI> vL = new ArrayList<GraphI>();
		for (PropertiesI<Object> e : l) {

			String id = (String) e.getProperty("id", true);
			PropertiesI<Object> ptsI = (PropertiesI) e.getProperty("properties", true);
			GraphI vI = ClientGraph.valueOf(id, ptsI, client);
			vL.add(vI);
		}

		return vL.iterator();

	}

}
