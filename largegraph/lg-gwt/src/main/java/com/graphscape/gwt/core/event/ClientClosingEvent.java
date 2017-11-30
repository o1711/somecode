/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 5, 2012
 */
package com.graphscape.gwt.core.event;

import com.graphscape.gwt.core.ClientI;

/**
 * @author wu
 * 
 */
public class ClientClosingEvent extends StateChangeEvent {

	public static final Type<ClientClosingEvent> TYPE = new Type<ClientClosingEvent>("client-closing");

	/**
	 * @param type
	 */
	public ClientClosingEvent(ClientI client) {
		super(TYPE, client);
	}

}
