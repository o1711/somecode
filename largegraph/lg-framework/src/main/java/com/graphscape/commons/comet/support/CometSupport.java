/**
 *  
 */
package com.graphscape.commons.comet.support;

import com.graphscape.commons.comet.CometI;
import com.graphscape.commons.comet.CometListenerI;

/**
 * @author wu
 * 
 */
public abstract class CometSupport extends CollectionCometListener implements CometI {

	private String id;

	private String protocol;
	
	private String credential;

	@Override
	public String getCredential() {
		return credential;
	}

	public CometSupport(String pro, String tid, String cre) {
		this.protocol = pro;
		this.id = tid;
		this.credential = cre;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.commons.websocket.api.WebSocketI#addListener(com.fs.commons.websocket.api.WsListenerI
	 * )
	 */
	@Override
	public void addListener(CometListenerI ln) {
		this.addListener(ln);
	}

	@Override
	public String getProtocol() {
		return this.protocol;
	}

}
