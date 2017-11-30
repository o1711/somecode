/**
 *  Dec 12, 2012
 */
package com.graphscape.commons.client.provider.default_.ajax;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.Semaphore;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.graphscape.commons.client.MessageClientI;
import com.graphscape.commons.client.MessageClientServiceProviderI;
import com.graphscape.commons.client.provider.default_.ajax.handler.ClosedHandler;
import com.graphscape.commons.client.provider.default_.ajax.handler.ConnectedHandler;
import com.graphscape.commons.client.provider.default_.ajax.handler.DefaultClientHandler;
import com.graphscape.commons.client.provider.default_.ajax.handler.ErrorHandler;
import com.graphscape.commons.client.provider.default_.ajax.handler.MessageHandler;
import com.graphscape.commons.client.support.MessageClientSupport;
import com.graphscape.commons.comet.provider.default_.AjaxCometHttpRequestHandler;
import com.graphscape.commons.comet.provider.default_.AjaxMsg;
import com.graphscape.commons.concurrent.FutureI;
import com.graphscape.commons.concurrent.provider.DefaultFuture;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.util.Path;
import com.graphscape.commons.util.ReaderUtil;

/**
 * @author wuzhen
 *         <p>
 *         TODO js test:
 *         <p>
 *         http://webtide.intalio.com/2011/08/websocket-example-server-client-
 *         and-loadtest/
 */
public class AjaxMessageClient extends MessageClientSupport {

	private static final Logger LOG = LoggerFactory.getLogger(AjaxMessageClient.class);

	protected Semaphore connected;// TODO remove this

	protected Semaphore disconnected;// TODO remove this machan

	protected HttpClient httpclient;

	protected String sid;

	protected Map<Path, ClientAjaxHandler> handlers;

	protected ClientAjaxHandler defaultHandler;

	private Timer heartBeatTimer;// NOTE this is not user layer heart beat.It is
									// ajax under layer heart beat.

	private TimerTask heartBeatTask;

	private boolean closing;

	private ExecutorService executor;

	public AjaxMessageClient(MessageClientServiceProviderI mcspi) {
		super(mcspi);
		this.executor = Executors.newSingleThreadExecutor();//

		
		HttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		this.httpclient = HttpClientBuilder.create().setConnectionManager(cm).build();
		this.handlers = new HashMap<Path, ClientAjaxHandler>();
		this.handlers.put(AjaxMsg.CONNECT.getSubPath("success"), new ConnectedHandler(this));
		this.handlers.put(AjaxMsg.CLOSE.getSubPath("success"), new ClosedHandler(this));
		this.handlers.put(AjaxMsg.MESSAGE, new MessageHandler(this));
		this.handlers.put(AjaxMsg.ERROR, new ErrorHandler(this));

		this.defaultHandler = new DefaultClientHandler(this);
		this.heartBeatTimer = new Timer();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.commons.commons.api.client.AClientI#connect()
	 */
	@Override
	public FutureI<MessageClientI> connect(final String credentical) {
		FutureTask<MessageClientI> rt = new FutureTask<MessageClientI>(new Callable<MessageClientI>() {

			@Override
			public MessageClientI call() throws Exception {
				return doConnect(credentical);
			}
		});
		this.connected = new Semaphore(0);
		this.executor.submit(rt);
		return DefaultFuture.valueOf(rt);
	}

	private MessageClientI doConnect(String credentical) {
		if (LOG.isTraceEnabled()) {
			LOG.trace("start connect");
		}

		if (this.sid != null) {
			throw new GsException("connected already!");
		}
		AjaxMsg am = new AjaxMsg(AjaxMsg.CONNECT);
		am.setProperty("credentical", credentical);
		
		this.doRequest(am);
		// wait the connect is ok.
		try {
			this.connected.acquire();
		} catch (InterruptedException e) {
			throw new GsException(e);
		}

		if (LOG.isTraceEnabled()) {
			LOG.trace("end of connect");
		}

		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.commons.commons.api.client.AClientI#close()
	 */
	@Override
	public void close() {
		if (!this.isConnected()) {
			throw new GsException("not connected yet.");
		}
		this.closing = true;
		AjaxMsg am = new AjaxMsg(AjaxMsg.CLOSE);
		this.doRequest(am);
		this.sid = null;// see as closed,even if server not response.

	}

	@Override
	public FutureI<MessageClientI> disconnect() {
		FutureTask<MessageClientI> ft = new FutureTask<MessageClientI>(new Callable<MessageClientI>() {

			@Override
			public MessageClientI call() throws Exception {
				AjaxMessageClient.this.doDisconnect();
				return null;
			}
		});
		this.disconnected = new Semaphore(0);
		this.executor.submit(ft);
		return DefaultFuture.valueOf(ft);
	}

	protected void doDisconnect() {
		this.close();
		try {
			this.disconnected.acquire();
		} catch (InterruptedException e) {
			throw GsException.toRuntimeException(e);
		}
		this.disconnected = null;
	}

	public void assertConnected() {
		if (!this.isConnected()) {
			throw new GsException("no sid found, not connected or disconnected?");
		}
	}

	public boolean isConnected() {
		return null != this.sid;
	}

	// TODO add callback for request.
	protected void doRequest(AjaxMsg am) {

		try {
			HttpPost req = new HttpPost(uri);
			// req.addHeader(AjaxCometHttpRequestHandler.HK_ACTION, "message");
			// req.addHeader(AjaxCometHttpRequestHandler.HK_SESSION_ID,
			// this.sid);
			req.setHeader("Connection", "close");// NOTE,
			req.setHeader("Content-Type", "application/json; charset=UTF-8");
			
			// only one element,but also in array.
			if (this.sid != null) {
				req.setHeader(AjaxCometHttpRequestHandler.HK_SESSION_ID, this.sid);
			}

			JsonObject json = am.toJsonObject();
			JsonArray arr = new JsonArray();
			arr.add(json);

			if (LOG.isTraceEnabled()) {//
				String msg = GSON4LOG.toJson(arr);
				LOG.trace("sending http request,ajax json message array:" + msg);
			}

			StringEntity entity = new StringEntity(gson.toJson(arr));
			req.setEntity(entity);

			// execute is here
			final HttpResponse res = httpclient.execute(req);
			StatusLine sl = res.getStatusLine();
			int scode = sl.getStatusCode();
			if (scode != 200) {
				throw new GsException("status code error,code:" + scode + ",reason:" + sl.getReasonPhrase()
						+ ",uri:" + this.uri);
			}
			// process response,
			// response is from here.
			InputStream is = res.getEntity().getContent();
			Reader r = new InputStreamReader(is);

			if (false) {//
				StringBuffer sb = ReaderUtil.readAsStringBuffer(r);

				r = ReaderUtil.toReader(sb);
				LOG.trace("start of tracing http client message");
				LOG.trace(sb.toString());
				LOG.trace("end of tracing http client message");

			}

			JsonArray jsa = (JsonArray) this.parser.parse(r);
			if (LOG.isTraceEnabled()) {
				String msg = GSON4LOG.toJson(jsa);
				LOG.trace("got http response,ajax json message array:" + msg);
			}
			for (int i = 0; i < jsa.size(); i++) {
				JsonObject amS = (JsonObject) jsa.get(i);

				AjaxMsg am2 = AjaxMsg.valueOf(amS);

				this.onAjaxMsg(am2);
			}

			// timer for heart beat.
			long delay = 0;// no need to delay more time,unless some error
							// situation.
			// TODO cancle the old task if not executed.
			if (this.heartBeatTask != null) {
				this.heartBeatTask.cancel();
			}
			this.heartBeatTask = new TimerTask() {

				@Override
				public void run() {
					AjaxMessageClient.this.trySendHeartBeat();
				}
			};
			this.heartBeatTimer.schedule(this.heartBeatTask, delay);

		} catch (Exception e) {
			throw new GsException(e);
		}
		if (LOG.isTraceEnabled()) {
			LOG.trace("end of doRequest");
		}
	}

	/**
	 * 
	 */
	protected void trySendHeartBeat() {
		if (!this.isConnected()) {
			return;// not connected.
		}
		if (this.closing) {
			return;
		}

		AjaxMsg am = new AjaxMsg(AjaxMsg.HEART_BEAT);
		this.doRequest(am);
	}

	/**
	 * @param am2
	 */
	private void onAjaxMsg(AjaxMsg am2) {

		if (LOG.isDebugEnabled()) {

		}

		Path path = am2.getPath();
		ClientAjaxHandler hdl = this.handlers.get(path);

		if (hdl == null) {
			hdl = this.defaultHandler;
		}
		ClientAjaxMsgContext amc = new ClientAjaxMsgContext();
		amc.am = am2;
		hdl.handle(amc);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.commons.commons.api.support.AClientSupport#sendMessage(java.lang
	 * .String)
	 */

	protected void doSendRawMessage(Object msg) {
		this.assertConnected();
		AjaxMsg am = new AjaxMsg(AjaxMsg.MESSAGE);
		am.setProperty(AjaxMsg.PK_CONTENT, msg);
		this.doRequest(am);
	}

	/**
	 * Called by handler for server close message.
	 */
	public void closedByServer() {
		this.sid = null;
		this.disconnected.release();
	}

	/**
	 * @param sid2
	 */
	public void conected(String sid2) {
		this.sid = sid2;
		this.connected.release();
	}

	/**
	 * 
	 */
	public void errorFromServer() {
		LOG.error("error from server.");
		
	}

	/**
	 * the entry point of server response.
	 * 
	 * @param msg
	 */
	public void rawMessageFromServer(Object msg) {
		this.onRawMessage(msg);
	}

}
