/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 11, 2012
 */
package com.fs.websocket.impl.jetty;

import java.io.IOException;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.UpgradeRequest;
import org.eclipse.jetty.websocket.api.WebSocketListener;
import org.eclipse.jetty.websocket.api.io.WebSocketBlockingConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.lang.FsException;
import com.fs.webcomet.api.CometListenerI;
import com.fs.webcomet.api.support.CometSupport;

/**
 * @author wu
 * 
 */
public class JettyCometImpl extends CometSupport implements WebSocketListener {

	private static final Logger LOG = LoggerFactory.getLogger(JettyCometImpl.class);

	protected Session connection;

	protected WebSocketBlockingConnection blocking;

	public JettyCometImpl(String id, UpgradeRequest ur) {
		super("websocket", id);
		if (LOG.isDebugEnabled()) {
			LOG.debug("" + ur.getHeaders());
		}
	}

	/*
	 * Dec 11, 2012
	 */
	@Override
	public void sendMessage(String msg) {
		try {
			this.blocking.write(msg);
		} catch (IOException e) {
			throw new FsException(e);
		}
	}

	@Override
	public void onWebSocketClose(int statusCode, String reason) {
		this.connection = null;
		LOG.info("onWebSocketClose,statusCode:" + statusCode + ",reason:" + reason);
		super.onClose(this, statusCode, reason);
	}

	@Override
	public void onWebSocketConnect(Session connection) {
		LOG.info("onWebSocketConnect");
		this.connection = connection;
		this.blocking = new WebSocketBlockingConnection(this.connection);
		super.onConnect(this);
	}

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void onWebSocketError(Throwable error) {
		LOG.error("onWebSocketException,error:" + error);
		super.onException(this, error);
	}

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void onWebSocketBinary(byte[] payload, int offset, int len) {
		// TODO
	}

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void onWebSocketText(String message) {
		//
		super.onMessage(this, message);
	}

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void addListener(CometListenerI ln) {
		//
		super.add(ln);
	}

}
