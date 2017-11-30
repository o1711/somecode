/**
 * 
 */
package com.graphscape.largegraph.client.provider.default_;

import java.net.URI;

import com.graphscape.commons.cache.CacheI;
import com.graphscape.commons.cache.provider.DefaultCache;
import com.graphscape.commons.client.MessageClientI;
import com.graphscape.commons.client.provider.default_.DefaultMessageClient;
import com.graphscape.commons.client.support.ProxyMessageClient;
import com.graphscape.commons.lang.HandlerI;
import com.graphscape.commons.message.MessageI;
import com.graphscape.largegraph.client.ClientI;
import com.graphscape.largegraph.core.VertexI;

/**
 * @author wuzhen
 * 
 */
public class DefaultClient extends ProxyMessageClient implements ClientI {

	public CacheI<VertexI> vertexCache = new DefaultCache<VertexI>();

	public DefaultClient(URI uri, HandlerI<MessageI> hdl) {
		this(new DefaultMessageClient(new DefaultMessageClientProvider(uri, hdl)));
	}

	/**
	 * @param t
	 */
	public DefaultClient(MessageClientI t) {
		super(t);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.largegraph.client.ClientI#getVertexCache()
	 */
	@Override
	public CacheI<VertexI> getVertexCache() {
		return this.vertexCache;
	}

}
