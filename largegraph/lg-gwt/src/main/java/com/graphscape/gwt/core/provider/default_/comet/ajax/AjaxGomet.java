/**
 * All right is from Author of the file,to be explained in comming days.
 * May 9, 2013
 */
package com.graphscape.gwt.core.provider.default_.comet.ajax;

import java.util.HashMap;
import java.util.Map;

import org.fusesource.restygwt.client.FailedStatusCodeException;
import org.fusesource.restygwt.client.JsonCallback;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.Resource;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.HandlerI;
import com.graphscape.gwt.core.UiException;
import com.graphscape.gwt.core.commons.Path;
import com.graphscape.gwt.core.endpoint.Address;
import com.graphscape.gwt.core.logger.UiLoggerFactory;
import com.graphscape.gwt.core.logger.UiLoggerI;
import com.graphscape.gwt.core.provider.default_.comet.GometSupport;
import com.graphscape.gwt.core.provider.default_.comet.ajax.AjaxGomet;
import com.graphscape.gwt.core.provider.default_.comet.ajax.AjaxMsg;
import com.graphscape.gwt.core.provider.default_.comet.ajax.ClientAjaxHandler;
import com.graphscape.gwt.core.provider.default_.comet.ajax.ClientAjaxMsgContext;
import com.graphscape.gwt.core.provider.default_.comet.ajax.handlers.ClosedHandler;
import com.graphscape.gwt.core.provider.default_.comet.ajax.handlers.ConnectedHandler;
import com.graphscape.gwt.core.provider.default_.comet.ajax.handlers.DefaultClientHandler;
import com.graphscape.gwt.core.provider.default_.comet.ajax.handlers.ErrorHandler;
import com.graphscape.gwt.core.provider.default_.comet.ajax.handlers.MessageHandler;
import com.graphscape.gwt.core.scheduler.SchedulerI;
import com.graphscape.gwt.core.state.State;
import com.graphscape.gwt.core.util.ExceptionUtil;

/**
 * @author wu
 * 
 */
public class AjaxGomet extends GometSupport {

	private static final UiLoggerI LOG = UiLoggerFactory.getLogger(AjaxGomet.class);

	// See serverlet in webserver
	public static final String HK_SESSION_ID = "x-fs-ajax-sessionId";

	private Resource resource;

	protected String sid;

	protected Map<Path, ClientAjaxHandler> handlers;

	protected ClientAjaxHandler defaultHandler;

	// private Timer heartBeatTimer;

	private SchedulerI scheduler;

	private int livingRequests;

	private long totalRequests;

	/**
	 * @param wso
	 */
	public AjaxGomet(ContainerI c, Address uri) {
		super(uri);
		this.scheduler = c.get(SchedulerI.class, true);
		this.resource = new Resource(this.uri.getUri());

		//
		this.handlers = new HashMap<Path, ClientAjaxHandler>();
		this.handlers.put(AjaxMsg.CONNECT.getSubPath("success"), new ConnectedHandler(this));
		this.handlers.put(AjaxMsg.CLOSE.getSubPath("success"), new ClosedHandler(this));
		this.handlers.put(AjaxMsg.MESSAGE, new MessageHandler(this));
		this.handlers.put(AjaxMsg.ERROR, new ErrorHandler(this));

		this.defaultHandler = new DefaultClientHandler(this);

	}

	/*
	 * May 9, 2013
	 */
	@Override
	public void open(long timeout) {
		super.open(timeout);
		AjaxMsg am = new AjaxMsg(AjaxMsg.CONNECT);
		this.doRequest(am);

	}

	protected void doRequest(final AjaxMsg am) {
		this.doRequest(am, null);
	}

	/**
	 * NOTE, the http Jun 17, 2013
	 */
	protected void doRequest(final AjaxMsg am, final HandlerI<String> onfailure) {

		this.livingRequests++;
		this.totalRequests++;
		if (LOG.isDebugEnabled()) {
			LOG.debug("doRequest,living request" + livingRequests + ",totalRequests:" + this.totalRequests
					+ ",msg:" + am);
		}
		JSONArray jsa = new JSONArray();
		jsa.set(0, am.getAsJsonObject());
		final String text = jsa.toString();
		Method m = this.resource.post().text(text);
		// Content-Type: text/plain; charset=ISO-8859-1
		m.header("Content-Type", "application/json; charset=UTF-8");
		m.header("Content-Length", "" + len(text));
		m.header("x-fs-debug", "debug:" + am.getPath());
		// session id is in request header.
		if (this.sid != null) {
			m.header(HK_SESSION_ID, this.sid);//
		}
		final String sid = this.sid;

		JsonCallback jcb = new JsonCallback() {

			@Override
			public void onFailure(Method method, Throwable exception) {
				//
				// TODO
				AjaxGomet.this.onRequestFailure(onfailure, am, method, exception); //
			}

			@Override
			public void onSuccess(Method method, JSONValue response) {
				//

				AjaxGomet.this.onRequestSuccess(am, method, response);
				//
			}
		};
		m.send(jcb);
		if (LOG.isDebugEnabled()) {
			LOG.debug("send out of message,totalRequests:" + this.totalRequests);
		}
	}

	private int len(String text) {
		return text.getBytes().length;
	}

	/**
	 * @param method
	 * @param response
	 */
	protected void onRequestSuccess(AjaxMsg am, Method method, JSONValue response) {

		this.livingRequests--;
		try {

			JSONArray jsa = (JSONArray) response;
			for (int i = 0; i < jsa.size(); i++) {
				JSONObject amS = (JSONObject) jsa.get(i);

				AjaxMsg am2 = new AjaxMsg(amS);

				this.onAjaxMsg(am2);
			}
		} finally {

			this.afterRequestSuccess(am);
		}
	}

	private void afterRequestSuccess(AjaxMsg am) {
		if (this.livingRequests > 0) {// only schedule after the last request is
			// responsed.
			return;
		}

		if (this.isState(CLOSED) || this.isState(CLOSING)) {
			// if is closing or closed, no more things to do.
			// the close success handler will call the tryClose() method.
			return;
		}

		String oldSid = am.getSessionId(false);
		final String fsid = this.sid;
		if (oldSid == null && fsid != null) {// new session opened.

		}

		if (oldSid != null && fsid == null) {// old session closed.

		}

		if (fsid == null) {// is openning
			// not opened successfully before,must not send heart beat.
			// Just ignore?
			return;
		}
		// only for the last request,
		// no request for now, so do a new request
		// immediately.
		// each request finish,should schedule a new request immediately.
		// if (!this.isOpen()) {
		// // if not open for some error,how to do?
		// return;
		// }

		this.scheduler.scheduleTimer(500, new HandlerI<Object>() {

			@Override
			public void handle(Object t) {
				AjaxGomet.this.doSendHeartBeat(fsid);
			}
		});
		//

	}

	/**
	 * @param method
	 * @param exception
	 */
	protected void onRequestFailure(HandlerI<String> onfailure, AjaxMsg req, Method method,
			Throwable exception) {
		this.livingRequests--;

		String data = "request failure for request:" + req + ",now:" + System.currentTimeMillis();

		if (exception instanceof FailedStatusCodeException) {
			FailedStatusCodeException fsce = (FailedStatusCodeException) exception;
			int code = fsce.getStatusCode();
			data += ",failed state code:" + code;
		}
		data += ",exceptions:\n" + ExceptionUtil.getStacktraceAsString(exception, "\n");

		// app-level callback.
		if (onfailure != null) {
			onfailure.handle(data);
		}
		//
		this.errorHandlers.handle(data);

		// HOW this happen?,may be because the network issue?

		this.tryCloseByError();
	}

	/**
	 * @param am2
	 */
	private void onAjaxMsg(AjaxMsg am2) {
		Path path = am2.getPath();
		ClientAjaxHandler hdl = this.handlers.get(path);

		if (hdl == null) {
			hdl = this.defaultHandler;
		}
		ClientAjaxMsgContext amc = new ClientAjaxMsgContext();
		amc.am = am2;
		hdl.handle(amc);

	}

	public void assertConnected() {
		if (!this.isConnected()) {
			throw new UiException("no sid");
		}
	}

	public boolean isConnected() {
		return null != this.sid;
	}

	/**
	 * Only send if connected,else stop the heart beat.
	 */
	protected boolean doSendHeartBeat(String sid) {

		AjaxMsg am = new AjaxMsg(AjaxMsg.HEART_BEAT);
		this.doRequest(am);
		return true;
	}

	public void tryCloseByError() {
		this.tryClose();
	}

	/**
	 * 
	 */
	public void tryClosedByServer() {
		this.tryClose();
	}

	public void tryClose() {
		if (this.isState(CLOSED)) {
			// already closed,not raise event again.
			return;// duplicated close?
		}
		this.sid = null;
		this.closed();
		this.closeHandlers.handle("closed");
	}

	/**
	 * @param sid2
	 */
	public void conected(String sid2) {
		this.sid = sid2;
		this.opened();

	}

	/**
	 * 
	 */
	public void errorFromServer(String error, String msg) {
		this.errorHandlers.handle(error + "," + msg);
		if (AjaxMsg.ERROR_CODE_SESSION_NOTFOUND.equals(error)) {//
			this.tryCloseByError();
		}
	}

	public void messageFromServer(String msg) {
		this.msgHandlers.handle(msg);
	}

	/*
	 * May 9, 2013
	 */
	@Override
	public void close() {
		//
		if (!this.isState(OPENED)) {
			throw new UiException("not openned");
		}
		this.state = CLOSING;
		// TODO send a async message to server?
		AjaxMsg am = new AjaxMsg(AjaxMsg.CLOSE);
		this.doRequest(am);
	}

	/*
	 * May 9, 2013
	 */
	@Override
	public void sendInternal(String jsS, HandlerI<String> onfailure) {
		//
		AjaxMsg am = new AjaxMsg(AjaxMsg.MESSAGE);
		am.setProperty(AjaxMsg.PK_TEXTMESSAGE, jsS);
		this.doRequest(am, onfailure);
	}

	/*
	 * May 9, 2013
	 */
	@Override
	public boolean isOpen() {
		//
		return this.sid != null;
	}

}
