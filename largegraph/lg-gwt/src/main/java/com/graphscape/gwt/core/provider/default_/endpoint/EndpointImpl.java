/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 20, 2012
 */
package com.graphscape.gwt.core.provider.default_.endpoint;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.Window;
import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.HandlerI;
import com.graphscape.gwt.core.endpoint.Address;
import com.graphscape.gwt.core.event.ClientClosingEvent;
import com.graphscape.gwt.core.message.MessageDispatcherI;
import com.graphscape.gwt.core.provider.default_.comet.GometI;
import com.graphscape.gwt.core.provider.default_.endpoint.protocols.AjaxProtocol;
import com.graphscape.gwt.core.provider.default_.endpoint.protocols.WsProtocolImpl;
import com.graphscape.gwt.core.support.EndpointSupport;

/**
 * @author wu
 * 
 */
public class EndpointImpl extends EndpointSupport {

	public static interface ProtocolI {
		public GometI createGomet(Address uri, boolean force);
	}

	private GometI socket;

	private Map<String, ProtocolI> protocols;

	private long openTimeout = 3000;//

	/**
	 * @param md
	 */
	public EndpointImpl(ContainerI c, Address uri, MessageDispatcherI md) {
		super(c, uri, md, new MessageCacheImpl(c));
		this.protocols = new HashMap<String, ProtocolI>();
		this.protocols.put("ws", new WsProtocolImpl());
		this.protocols.put("wss", new WsProtocolImpl());

		this.protocols.put("http", new AjaxProtocol(c));
		this.protocols.put("https", new AjaxProtocol(c));

	}

	/*
	 * Dec 20, 2012
	 */
	@Override
	protected void doAttach() {
		super.doAttach();

	}

	/**
	 * Apr 4, 2013
	 */
	protected void onClientClosing(ClientClosingEvent t) {
		this.close();
	}

	@Override
	public void close() {
		this.socket.close();
	}

	@Override
	public void open() {
		super.open();

		String proS = uri.getProtocol();

		ProtocolI pro = this.protocols.get(proS);

		this.socket = pro.createGomet(uri, false);
		this.socket.open(this.openTimeout);
		if (this.socket == null) {
			Window.alert("protocol is not support:" + proS);
		}

		this.socket.addOpenHandler(new HandlerI<GometI>() {

			@Override
			public void handle(GometI t) {
				//
				EndpointImpl.this.onConnected();
			}
		});
		this.socket.addMessageHandler(new HandlerI<String>() {

			@Override
			public void handle(String t) {
				//
				EndpointImpl.this.onMessage(t);

			}
		});
		this.socket.addCloseHandler(new HandlerI<String>() {

			@Override
			public void handle(String t) {
				//
				EndpointImpl.this.onClosed(t, "");
			}
		});
		this.socket.addErrorHandler(new HandlerI<String>() {

			@Override
			public void handle(String t) {
				//
				EndpointImpl.this.onError(t);
			}
		});

	}

	@Override
	protected boolean isNativeSocketOpen() {
		if (this.socket == null) {
			return false;
		}

		boolean rs = this.socket.isOpen();
		return rs;

	}

	@Override
	protected void doSendMessage(String jsS, HandlerI<String> failureHanlder) {
		this.socket.send(jsS, failureHanlder);
	}

	@Override
	public void destroy() {
		this.parent(null);
		this.eventDispatcher.cleanAllHanlders();
	}

}
