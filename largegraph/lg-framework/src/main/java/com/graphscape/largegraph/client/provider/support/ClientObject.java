/**
 * Dec 27, 2013
 */
package com.graphscape.largegraph.client.provider.support;

import com.graphscape.commons.concurrent.FutureI;
import com.graphscape.commons.lang.CallbackI;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.lang.PropertiesI;
import com.graphscape.commons.lang.support.ProxyProperties;
import com.graphscape.commons.message.MessageI;
import com.graphscape.commons.util.ProxyFuture4;
import com.graphscape.largegraph.client.ClientI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class ClientObject extends ProxyProperties<Object> {
	protected ClientI client;

	/**
	 * @param t
	 */
	public ClientObject(ClientI client, PropertiesI<Object> pts) {
		super(pts);
		this.client = client;
	}

	public ClientObject(ClientI client) {
		this(client, null);
	}


	public ClientI getClient() {
		return client;
	}
}
