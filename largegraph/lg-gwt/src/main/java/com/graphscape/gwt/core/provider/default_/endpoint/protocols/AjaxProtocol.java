/**
 * All right is from Author of the file,to be explained in comming days.
 * May 12, 2013
 */
package com.graphscape.gwt.core.provider.default_.endpoint.protocols;

import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.endpoint.Address;
import com.graphscape.gwt.core.provider.default_.comet.GometI;
import com.graphscape.gwt.core.provider.default_.comet.ajax.AjaxGomet;
import com.graphscape.gwt.core.provider.default_.endpoint.EndpointImpl.ProtocolI;

/**
 * @author wu
 * 
 */
public class AjaxProtocol implements ProtocolI {

	private ContainerI c;

	public AjaxProtocol(ContainerI c) {
		this.c = c;
	}

	/*
	 * May 9, 2013
	 */
	@Override
	public GometI createGomet(Address uri, boolean force) {
		//

		AjaxGomet rt = new AjaxGomet(this.c, uri);
		return rt;

	}

}
