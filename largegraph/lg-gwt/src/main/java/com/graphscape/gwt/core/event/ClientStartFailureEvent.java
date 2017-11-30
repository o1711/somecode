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
public class ClientStartFailureEvent extends ClientEvent {
	public static Type<ClientStartFailureEvent> TYPE = new Type<ClientStartFailureEvent>(
			ClientEvent.TYPE, "start-failure");

	/** */
	public ClientStartFailureEvent(ClientI client) {
		super(TYPE, client);
	}

}