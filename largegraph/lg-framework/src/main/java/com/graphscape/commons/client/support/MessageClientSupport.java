/**
 *  Dec 12, 2012
 */
package com.graphscape.commons.client.support;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.graphscape.commons.client.MessageClientI;
import com.graphscape.commons.client.MessageClientServiceProviderI;
import com.graphscape.commons.concurrent.FutureI;
import com.graphscape.commons.container.ContainerI;
import com.graphscape.commons.lang.HandlerI;
import com.graphscape.commons.marshal.MarshallerI;
import com.graphscape.commons.message.MessageI;
import com.graphscape.commons.util.Path;

/**
 * @author wuzhen
 *         <p>
 *         TODO js test:
 *         <p>
 *         http://webtide.intalio.com/2011/08/websocket-example-server-client-
 *         and-loadtest/
 */
public abstract class MessageClientSupport implements MessageClientI {

	private static final Logger LOG = LoggerFactory.getLogger(MessageClientSupport.class);

	protected URI uri;

	protected String name;

	protected HandlerI<MessageI> handler;

	protected MarshallerI codec;

	protected long lastSendMessageTs;

	protected ContainerI container;

	protected JsonParser parser = new JsonParser();

	protected Gson gson;

	protected static Gson GSON4LOG = new GsonBuilder().setPrettyPrinting().create();

	protected HeartBeat heartBeat;// user layer heart beat support.

	protected ResponseWatcher watcher;
	
	protected boolean encodeContentAsJson = true;

	public MessageClientSupport(MessageClientServiceProviderI mcspi) {
		GsonBuilder jb = new GsonBuilder();
		if (mcspi.isRequestMessageFormatPrettyPrint()) {
			jb.setPrettyPrinting();
		}

		this.gson = jb.create();

		this.uri = mcspi.getUri();
		this.handler = mcspi.getMessageCallback();
		this.codec = mcspi.getMarshaller();

		Path path = mcspi.getHeartBeatMessagePath();
		if (path != null) {// neet heart beat
			this.heartBeat = new HeartBeat(path);
		}
		this.watcher = new ResponseWatcher(this);
	}

	/**
	 * should be called by implementation to notify server side message. It is
	 * response for request or other message type from server.
	 * 
	 * @param message
	 */
	protected void onRawMessage(Object message) {
		try {

			JsonArray ser = null;
			if (message instanceof String) {
				ser = (JsonArray) parser.parse((String) message);

			} else {
				ser = (JsonArray) message;
			}
			MessageI msg = (MessageI) this.codec.unmarshal(ser);
			// first process response future
			this.watcher.onMessage(msg);
			this.handler.handle(msg);
		} catch (Throwable t) {
			LOG.error("onWebSocketText error", t);
		}
	}

	@Override
	public FutureI<MessageI> putMessage(MessageI msg) {
		return this.watcher.putMessage(msg);
	}

	@Override
	public void sendMessage(MessageI msg) {
		this.doSendMessage(msg);
		if (this.heartBeat == null) {
			return;
		}
		this.heartBeat.reset(this);

	}

	public void doSendMessage(MessageI msg) {

		
		Object content = this.codec.marshal(msg);//
		
		this.doSendRawMessage(content);
		this.lastSendMessageTs = System.currentTimeMillis();

	}

	protected abstract void doSendRawMessage(Object msg);

	/*
	 * Jan 26, 2013
	 */
	@Override
	public String getName() {
		//
		return this.name;
	}

	@Override
	public int getIdleTime() {
		long now = System.currentTimeMillis();
		return (int) (now - this.lastSendMessageTs);
	}

}
