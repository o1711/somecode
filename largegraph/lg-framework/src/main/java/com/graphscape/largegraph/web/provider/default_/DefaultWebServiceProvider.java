/**
 * Dec 15, 2013
 */
package com.graphscape.largegraph.web.provider.default_;

import com.graphscape.commons.comet.spi.CometAuthProviderI;
import com.graphscape.largegraph.server.LargeGraphServerI;
import com.graphscape.largegraph.web.spi.WebServiceProviderI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class DefaultWebServiceProvider implements WebServiceProviderI {
	LargeGraphServerI lgServer;
	CometAuthProviderI ap;
	public DefaultWebServiceProvider(LargeGraphServerI lgs,CometAuthProviderI ap) {
		this.lgServer = lgs;
		this.ap = ap;
	}

	@Override
	public LargeGraphServerI getLargeGraphServer() {
		return this.lgServer;
	}

	@Override
	public CometAuthProviderI getAuthProvider() {
		
		return this.ap;
	}

}
