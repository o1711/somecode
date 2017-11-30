/**
 * Jul 20, 2012
 */
package com.graphscape.gwt.core.event;

import com.graphscape.gwt.core.ClientI;
import com.graphscape.gwt.core.endpoint.EndPointI;

/**
 * @author wu
 * 
 */
public class AfterClientStartEvent extends ClientEvent {
	public static Type<AfterClientStartEvent> TYPE = new Type<AfterClientStartEvent>(ClientEvent.TYPE,
			"started");

	/** */
	public AfterClientStartEvent(ClientI client) {
		super(TYPE, client);
	}

	public ClientI getClient() {
		return (ClientI) this.source;
	}

	public EndPointI getEndPoint() {
		return this.getClient().getEndpoint(true);
	}

}