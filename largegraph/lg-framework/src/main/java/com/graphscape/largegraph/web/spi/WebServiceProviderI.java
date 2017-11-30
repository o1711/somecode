/**
 * Dec 15, 2013
 */
package com.graphscape.largegraph.web.spi;

import com.graphscape.commons.comet.spi.CometAuthProviderI;
import com.graphscape.largegraph.server.LargeGraphServerI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public interface WebServiceProviderI {
	public LargeGraphServerI getLargeGraphServer();
	public CometAuthProviderI getAuthProvider();

}
