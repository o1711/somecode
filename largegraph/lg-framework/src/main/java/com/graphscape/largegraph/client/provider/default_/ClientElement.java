/**
 * Dec 22, 2013
 */
package com.graphscape.largegraph.client.provider.default_;

import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.lang.HandlerI;
import com.graphscape.commons.lang.PropertiesI;
import com.graphscape.largegraph.client.ClientI;
import com.graphscape.largegraph.client.provider.support.ClientObject;
import com.graphscape.largegraph.core.ElementI;
import com.graphscape.largegraph.core.EventContext;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class ClientElement extends ClientObject implements ElementI {

	protected String id;

	/**
	 * @param t
	 */
	public ClientElement(ClientI client, String id, PropertiesI<Object> t) {
		super(client, t);
		this.id = id;
	}

	@Override
	public String getId() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.largegraph.core.ElementI#getEventHandler()
	 */
	@Override
	public HandlerI<EventContext> getEventHandler() {
		throw new GsException("not supported yet");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.largegraph.core.ElementI#setEventHandler(java.lang.String)
	 */
	@Override
	public void setEventHandler(String type) {
		throw new GsException("not suported yet");
	}

}
