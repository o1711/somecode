/**
 * Dec 13, 2013
 */
package com.graphscape.largegraph.client;

import com.graphscape.commons.cache.CacheI;
import com.graphscape.commons.client.MessageClientI;
import com.graphscape.largegraph.core.VertexI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public interface ClientI extends MessageClientI {
	public CacheI<VertexI> getVertexCache();
}
