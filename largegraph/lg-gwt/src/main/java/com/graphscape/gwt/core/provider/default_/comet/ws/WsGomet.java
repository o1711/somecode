/**
 * All right is from Author of the file,to be explained in comming days.
 * May 9, 2013
 */
package com.graphscape.gwt.core.provider.default_.comet.ws;

import com.graphscape.gwt.core.HandlerI;
import com.graphscape.gwt.core.endpoint.Address;
import com.graphscape.gwt.core.html5.CloseEventJSO;
import com.graphscape.gwt.core.html5.ErrorJSO;
import com.graphscape.gwt.core.html5.EventJSO;
import com.graphscape.gwt.core.html5.WebSocketJSO;
import com.graphscape.gwt.core.logger.UiLoggerFactory;
import com.graphscape.gwt.core.logger.UiLoggerI;
import com.graphscape.gwt.core.provider.default_.comet.GometSupport;

/**
 * @author wu
 * 
 */
public class WsGomet extends GometSupport {

	private UiLoggerI LOG = UiLoggerFactory.getLogger(WsGomet.class);

	private WebSocketJSO socket;

	/**
	 * @param wso
	 */
	public WsGomet(Address uri) {
		super(uri);
	}

	/**
	 * May 12, 2013
	 */
	protected void onMessage(EventJSO t) {
		//
		String msg = t.getData();
		this.msgHandlers.handle(msg);

	}

	/**
	 * May 12, 2013
	 */
	protected void onError(ErrorJSO t) {
		String msg = "" + t.getData();
		this.errorHandlers.handle(msg);
	}

	/**
	 * May 12, 2013
	 */
	protected void onClose(CloseEventJSO t) {
		//
		String msg = "" + t.getCode() + "," + t.getReason();
		this.closeHandlers.handle(msg);
	}

	private void onOpen(EventJSO evt) {
		this.opened();
	}

	/*
	 * May 9, 2013
	 */
	@Override
	public void open(long timeout) {
		super.open(timeout);
		// ws hs no open method,just when init to open it.

		String uriS = uri.getUri();

		WebSocketJSO wso = WebSocketJSO.newInstance(uriS, false);
		if (wso == null) {
			this.errorHandlers.handle("websocket not supported by browser?");
			return;
		}

		this.socket = wso;

		this.socket.onOpen(new HandlerI<EventJSO>() {

			@Override
			public void handle(EventJSO t) {
				WsGomet.this.onOpen(t);
			}
		});
		//
		this.socket.onClose(new HandlerI<CloseEventJSO>() {

			@Override
			public void handle(CloseEventJSO t) {
				WsGomet.this.onClose(t);
			}
		});

		//
		this.socket.onError(new HandlerI<ErrorJSO>() {

			@Override
			public void handle(ErrorJSO t) {
				WsGomet.this.onError(t);
			}
		});
		this.socket.onMessage(new HandlerI<EventJSO>() {

			@Override
			public void handle(EventJSO t) {
				WsGomet.this.onMessage(t);
			}
		});
	}

	/*
	 * May 9, 2013
	 */
	@Override
	public void close() {
		this.socket.close();
	}

	/*
	 * May 9, 2013
	 */
	@Override
	protected void sendInternal(String jsS, HandlerI<String> onfailure) {

		// not supported failure callback.
		if (onfailure != null) {
			//
		}
		this.socket.send(jsS);
	}

	/*
	 * May 9, 2013
	 */
	@Override
	public boolean isOpen() {
		//
		return this.socket.isOpen();
	}

}
