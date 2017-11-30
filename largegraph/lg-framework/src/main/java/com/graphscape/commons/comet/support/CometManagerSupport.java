/**
 *  Dec 11, 2012
 */
package com.graphscape.commons.comet.support;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.graphscape.commons.comet.CometI;
import com.graphscape.commons.comet.CometListenerI;
import com.graphscape.commons.comet.CometManagerI;
import com.graphscape.commons.comet.spi.CometServiceProviderI;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.util.StringUtil;

/**
 * @author wuzhen
 *         <p>
 *         1-1 mapping to Servlet
 */
public class CometManagerSupport implements CometManagerI, CometListenerI {

	protected CometListenerI listener;// user listener


	protected Map<String, CometI> socketMap;
	protected CometServiceProviderI spi;

	public CometManagerSupport(CometServiceProviderI spi) {
		this.spi = spi;
		this.listener = spi.getCometListener();

		this.socketMap = Collections
				.synchronizedMap(new HashMap<String, CometI>());
	}

	/*
	 * Dec 11, 2012
	 */
	@Override
	public CometI getSocket(String id) {
		//
		return this.getSocket(id, false);

	}

	@Override
	public CometI getSocket(String id, boolean force) {
		CometI rt = this.socketMap.get(id);
		if (rt == null && force) {
			throw new GsException("no websocket:" + id);
		}
		return rt;
	}

	protected String nextId() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

	/*
	 * Dec 12, 2012
	 */
	@Override
	public List<CometI> getSocketList() {
		//
		return new ArrayList<CometI>(this.socketMap.values());
	}

	/*
	 * Dec 12, 2012
	 */

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void onClose(CometI ws, int statusCode, String reason) {
		//
		CometI old = this.socketMap.remove(ws.getId());
		if (old == null) {
			throw new GsException("bug,no this websocket:" + ws.getId());
		}
		this.listener.onClose(ws, statusCode, reason);
	}

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void onMessage(CometI ws, Object ms) {
		this.listener.onMessage(ws, ms);
	}

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void onException(CometI ws, Throwable t) {
		//
		this.listener.onException(ws, t);
	}

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void onConnect(CometI ws) {
		this.socketMap.put(ws.getId(), ws);//
		this.listener.onConnect(ws);
	}
	@Override
	public void onMessage(CometI ws, Reader reader) {
		String s = StringUtil.readAsString(reader);
		this.onMessage(ws, s);
	}

}
