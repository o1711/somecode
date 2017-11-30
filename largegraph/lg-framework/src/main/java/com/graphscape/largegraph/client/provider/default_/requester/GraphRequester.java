/**
 * Dec 29, 2013
 */
package com.graphscape.largegraph.client.provider.default_.requester;

import com.graphscape.largegraph.client.ClientI;
import com.graphscape.largegraph.client.provider.support.RequesterSupport;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public abstract class GraphRequester<T> extends RequesterSupport<T> {

	protected String graphId;

	/**
	 * @param client
	 */
	public GraphRequester(String gid, ClientI client) {
		super(client);
		this.graphId = gid;
	}

}
