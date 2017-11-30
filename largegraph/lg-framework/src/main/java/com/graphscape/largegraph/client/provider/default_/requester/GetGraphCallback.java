/**
 * Dec 28, 2013
 */
package com.graphscape.largegraph.client.provider.default_.requester;

import com.graphscape.commons.concurrent.FutureI;
import com.graphscape.commons.lang.ErrorInfos;
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
public class GetGraphCallback extends RequesterSupport<GraphI> {

	protected String id;
	/**
	 * @param lg
	 * @param client
	 */
	public GetGraphCallback(String id,ClientI lg) {
		super(lg);
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see com.graphscape.largegraph.client.provider.support.Requester#buildRequest()
	 */
	@Override
	public MessageI buildRequest() {
		MessageI m2 = new DefaultMessage(null, Path.valueOf("/lg/get-graph"));
		m2.setPayload("id", id);
		return m2;
	}

	/* (non-Javadoc)
	 * @see com.graphscape.largegraph.client.provider.support.Requester#doConvert(com.graphscape.commons.message.MessageI)
	 */
	@Override
	public GraphI doConvert(MessageI t) {

		PropertiesI<Object> rt = (PropertiesI) t.getPayload("properties", true);
		String id = (String) t.getPayload("id", true);

		return ClientGraph.valueOf(id, rt, this.client);

	}

}
