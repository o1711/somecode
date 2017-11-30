/**
 *  
 */
package com.graphscape.gwt.core;

/**
 * @author wu
 *
 */
/**
 * All right is from Author of the file,to be explained in comming days.
 * Sep 22, 2012
 */

import com.google.gwt.core.shared.GWT;
import com.graphscape.gwt.core.ClientLoader;
import com.graphscape.gwt.core.commons.ProtocolPort;
import com.graphscape.gwt.core.core.Event;
import com.graphscape.gwt.core.core.Event.EventHandlerI;
import com.graphscape.gwt.core.spi.GwtSPI;

/**
 * @author wu Test support.
 */
public abstract class ClientLoader {

	private static ClientLoader ME = GWT.create(ClientLoader.class);

	protected ProtocolPort protocolPort;// for test purpose.

	public static ClientLoader getInstance() {
		return ME;
	}

	public ClientLoader protocolPort(ProtocolPort pp) {
		protocolPort = pp;
		return this;
	}

	public abstract GwtSPI.Factory getOrLoadClient(GwtSPI[] spis, EventHandlerI<Event> l);

	public abstract GwtSPI.Factory loadClient(GwtSPI[] spis, EventHandlerI<Event> l);

}
