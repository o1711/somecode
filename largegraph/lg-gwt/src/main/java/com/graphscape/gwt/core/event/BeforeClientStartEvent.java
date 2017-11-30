/**
 * Jul 20, 2012
 */
package com.graphscape.gwt.core.event;

import com.graphscape.gwt.core.ClientI;

/**
 * @author wu
 * 
 */
public class BeforeClientStartEvent extends ClientEvent {
	public static Type<BeforeClientStartEvent> TYPE = new Type<BeforeClientStartEvent>(ClientEvent.TYPE, "before-start");

	/** */
	public BeforeClientStartEvent(ClientI client) {
		super(TYPE, client);
	}

}