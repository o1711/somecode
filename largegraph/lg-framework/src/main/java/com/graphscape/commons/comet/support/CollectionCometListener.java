/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 12, 2012
 */
package com.graphscape.commons.comet.support;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.graphscape.commons.comet.CometI;
import com.graphscape.commons.comet.CometListenerI;
import com.graphscape.commons.lang.support.HasAttributeSupport;
import com.graphscape.commons.util.StringUtil;

/**
 * @author wu
 * 
 */
public class CollectionCometListener extends HasAttributeSupport implements CometListenerI {

	protected List<CometListenerI> listeners;

	public CollectionCometListener() {
		this.listeners = new ArrayList<CometListenerI>();
	}

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void onMessage(CometI ws, Object msg) {
		//
		for (CometListenerI l : this.listeners) {
			l.onMessage(ws, msg);
		}
	}

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void onException(CometI ws, Throwable t) {
		//
		//
		for (CometListenerI l : this.listeners) {
			l.onException(ws, t);
		}
	}

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void onConnect(CometI ws) {
		//
		//
		for (CometListenerI l : this.listeners) {
			l.onConnect(ws);
		}
	}

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void onClose(CometI ws, int statusCode, String reason) {
		//
		//
		for (CometListenerI l : this.listeners) {
			l.onClose(ws, statusCode, reason);
		}
	}

	/**
	 * Dec 12, 2012
	 */
	public void add(CometListenerI ln) {
		this.listeners.add(ln);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.commons.websocket.api.WsListenerI#onMessage(com.fs.commons.websocket.api.WebSocketI
	 * , java.io.Reader)
	 */
	@Override
	public void onMessage(CometI ws, Reader reader) {
		String s = StringUtil.readAsString(reader);
		this.onMessage(ws, s);
	}
}
