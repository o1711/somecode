/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 20, 2012
 */
package com.graphscape.gwt.core.html5;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Window;
import com.graphscape.gwt.core.HandlerI;
import com.graphscape.gwt.core.UiException;
import com.graphscape.gwt.core.html5.AbstractJSO;
import com.graphscape.gwt.core.html5.CloseEventJSO;
import com.graphscape.gwt.core.html5.ErrorJSO;
import com.graphscape.gwt.core.html5.EventJSO;
import com.graphscape.gwt.core.html5.ReadyState;
import com.graphscape.gwt.core.html5.WebSocketJSO;
import com.graphscape.gwt.core.support.ErrorReportProxyHandler;

/**
 * @author wu
 *         <p>
 *         http://tools.ietf.org/html/draft-ietf-hybi-thewebsocketprotocol-17
 *         <p>
 *         http://dev.w3.org/html5/websockets/#the-websocket-interface
 */
public final class WebSocketJSO extends AbstractJSO {
	//
	// public static final short CONNECTING = 0;
	// public static final short OPEN = 1;
	// public static final short CLOSING = 2;
	// public static final short CLOSED = 3;

	protected WebSocketJSO() {

	}

	public static WebSocketJSO newInstance(String uri, boolean force) {

		WebSocketJSO rt = tryCreate(uri);
		if (force && rt == null) {
			String agent = Window.Navigator.getUserAgent();
			throw new UiException("browser not support web socket,agent:" + agent);
		}
		return rt;
	}

	private static native WebSocketJSO tryCreate(String uri)
	/*-{
	    //$wnd.alert('WebSocketinGWT:'+$wnd.WebSocket);
	    if($wnd.WebSocket){
	    	
	    	//note: new WebSocket(uri) not work with Flash socket by web-socket-js package.
	    	 
			var  rt = new $wnd.WebSocket(uri); 		 
			return rt;
		}
		if($wnd.MozWebSocket){
			var  rt = new $wnd.MozWebSocket(uri); 		 
			return rt;
		}
		return null;
	}-*/;

	public final void onOpen(final HandlerI<EventJSO> handler) {

		this.onEvent("onopen", new HandlerI<JavaScriptObject>() {

			@Override
			public void handle(JavaScriptObject t) {
				EventJSO jso = t.cast();
				handler.handle(jso);
			}

		});

	}

	public final void onClose(final HandlerI<CloseEventJSO> handler) {

		this.onEvent("onclose", new HandlerI<JavaScriptObject>() {

			@Override
			public void handle(JavaScriptObject t) {
				CloseEventJSO jso = t.cast();
				handler.handle(jso);
			}

		});

	}

	public final void onMessage(final HandlerI<EventJSO> handler) {

		this.onEvent("onmessage", new HandlerI<JavaScriptObject>() {

			@Override
			public void handle(JavaScriptObject t) {
				EventJSO jso = t.cast();
				handler.handle(jso);
			}

		});

	}

	public final void onError(final HandlerI<ErrorJSO> handler) {

		this.onEvent("onerror", new HandlerI<JavaScriptObject>() {

			@Override
			public void handle(JavaScriptObject t) {
				ErrorJSO jso = t.cast();
				handler.handle(jso);
			}

		});

	}

	public void onEvent(String event, HandlerI<JavaScriptObject> handler) {
		this.onEventInternal(event, new ErrorReportProxyHandler<JavaScriptObject>(handler));
	}

	private native void onEventInternal(String event, HandlerI<JavaScriptObject> handler)
	/*-{
		this[event] = function (evt){
			handler.@com.graphscape.gwt.core.HandlerI::handle(Ljava/lang/Object;)(evt);
		};
	}-*/;

	public native void close()
	/*-{
		this.close();
	}-*/;

	public native void send(String msg)
	/*-{
		this.send(msg);
	}-*/;

	public final ReadyState getReadyState() {
		short rs = this.getReadyStateValue();
		return ReadyState.getReadyState(rs);
	}

	public native short getReadyStateValue()
	/*-{
		var rt = this.readyState;
		//FOR:Caused by: com.google.gwt.dev.shell.HostedModeException: 
		//Something other than a short was returned from JSNI method 
		//'com.graphscape.gwt.commons.html5.websocket.WebSocketJSO::getReadyState()': 
		//JS value of type undefined, expected short
		if(rt == undefined){
			rt = -1;
		}
		return rt;
	}-*/;

	/**
	 * @return
	 */
	public final boolean isOpen() {
		// TODO Auto-generated method stub
		return this.getReadyState().equals(ReadyState.OPEN);
	}
}
