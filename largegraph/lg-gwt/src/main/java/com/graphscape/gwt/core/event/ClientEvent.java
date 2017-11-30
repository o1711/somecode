/**
 * Jul 20, 2012
 */
package com.graphscape.gwt.core.event;

import com.graphscape.gwt.core.ClientI;
import com.graphscape.gwt.core.core.Event;

/**
 * @author wu
 * 
 */
public abstract class ClientEvent extends Event {
	public static Type<ClientEvent> TYPE = new Type<ClientEvent>("client");

	/** */
	public ClientEvent(Type<? extends ClientEvent> type, ClientI client) {
		super(type, client);
	}

}