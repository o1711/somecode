/**
 * All right is from Author of the file,to be explained in comming days.
 * May 12, 2013
 */
package com.graphscape.gwt.core.provider.default_.endpoint.protocols;

import com.graphscape.gwt.core.endpoint.Address;
import com.graphscape.gwt.core.provider.default_.comet.GometI;
import com.graphscape.gwt.core.provider.default_.comet.ws.WsGomet;
import com.graphscape.gwt.core.provider.default_.endpoint.EndpointImpl.ProtocolI;

/**
 * @author wu
 * 
 */
public class WsProtocolImpl implements ProtocolI {

	/*
	 * May 9, 2013
	 */
	@Override
	public GometI createGomet(Address uri, boolean force) {
		//
		WsGomet rt = new WsGomet(uri);
		
		return rt;

	}

}
