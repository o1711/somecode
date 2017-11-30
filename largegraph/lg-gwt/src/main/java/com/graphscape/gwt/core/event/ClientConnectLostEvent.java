/**
 * Jul 20, 2012
 */
package com.graphscape.gwt.core.event;

import com.graphscape.gwt.core.ClientI;

/**
 * @author wu
 * 
 * 
 */
public class ClientConnectLostEvent extends ClientEvent {
	public static Type<ClientConnectLostEvent> TYPE = new Type<ClientConnectLostEvent>(ClientEvent.TYPE,
			"connection-lost");

	/** */
	public ClientConnectLostEvent(ClientI client) {
		super(TYPE, client);
	}

}