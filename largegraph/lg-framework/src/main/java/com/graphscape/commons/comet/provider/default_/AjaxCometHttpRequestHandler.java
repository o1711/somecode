/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 27, 2012
 */
package com.graphscape.commons.comet.provider.default_;

import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.graphscape.commons.comet.provider.default_.handler.AjaxCloseHandler;
import com.graphscape.commons.comet.provider.default_.handler.AjaxConnectHandler;
import com.graphscape.commons.comet.provider.default_.handler.AjaxDefaultHandler;
import com.graphscape.commons.comet.provider.default_.handler.AjaxHeartBeatHandler;
import com.graphscape.commons.comet.provider.default_.handler.AjaxMessageHandler;
import com.graphscape.commons.comet.spi.CometServiceProviderI;
import com.graphscape.commons.http.HttpRequestContextI;
import com.graphscape.commons.http.HttpRequestHandlerI;
import com.graphscape.commons.http.HttpRequestI;
import com.graphscape.commons.http.HttpResponseI;
import com.graphscape.commons.http.ResponseStatus;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.session.SessionI;
import com.graphscape.commons.session.SessionManagerI;
import com.graphscape.commons.util.Path;
import com.graphscape.commons.util.TimeAndUnit;

/**
 * @author wu
 *         <P>
 *         This is the manager of ajax session/comet.
 */
public class AjaxCometHttpRequestHandler implements HttpRequestHandlerI {

	private static final Logger LOG = LoggerFactory.getLogger(AjaxCometHttpRequestHandler.class);

	// NOTE,must same as client.
	public static final String HK_SESSION_ID = "x-gs-ajax-sessionId";

	public static final String SK_COMET = "ajaxComet";

	protected AjaxCometManagerImpl manager;

	protected Map<Path, AjaxMsgHandler> handlers;

	protected AjaxMsgHandler defaultAjaxMsgHandler;

	protected SessionManagerI sessions;

	protected TimeAndUnit maxIdleTimeout = TimeAndUnit.valueOf(5, TimeUnit.MINUTES);// default

	protected TimeAndUnit timeoutForFirstMessage = maxIdleTimeout.divide(2);

	protected CometServiceProviderI spi;

	public AjaxCometHttpRequestHandler(CometServiceProviderI spi) {
		this.spi = spi;

		this.sessions = this.spi.getSessionManager();

		this.manager = new AjaxCometManagerImpl(spi);

		this.handlers = new HashMap<Path, AjaxMsgHandler>();
		// default handler
		this.defaultAjaxMsgHandler = new AjaxDefaultHandler(this.sessions, this.manager);
		// handlers
		this.handlers.put(AjaxMsg.CLOSE, new AjaxCloseHandler(this.sessions, this.manager));
		this.handlers.put(AjaxMsg.CONNECT, new AjaxConnectHandler(this.sessions, this.manager, this.spi));
		this.handlers.put(AjaxMsg.MESSAGE, new AjaxMessageHandler(this.sessions, this.manager));
		this.handlers.put(AjaxMsg.HEART_BEAT, new AjaxHeartBeatHandler(this.sessions, this.manager));
	}

	@Override
	public void messageReceived(HttpRequestContextI ctx) {
		// TODO Auto-generated method stub
		HttpRequestI req = ctx.getRequest();
		HttpResponseI res = ctx.response(ResponseStatus.OK);
		if (LOG.isDebugEnabled()) {

			String ccode = req.getCharset();
			String ctype = req.getContentType();
			if (!"utf-8".equalsIgnoreCase(ccode)) {//
				throw new GsException("only support utf-8,but got:" + ccode
						+ ",please set content type to:application/json; charset=UTF-8");
			}
			long len = req.getContentLength();
			if (len == 0) {//
				throw new GsException("len is zero");
			}
		}

		res.setContentType("application/json; charset=UTF-8");
		// if reader cannot read, check Content-Length
		// http://osdir.com/ml/java.jetty.general/2002-12/msg00198.html
		// if (false) {// debug
		// String str = StringUtil.readAsString(reader);
		// if (LOG.isDebugEnabled()) {
		// LOG.debug("request text:" + str);
		// }
		// reader = new StringReader(str);
		// }

		// find the session
		String sid = req.getHeader(HK_SESSION_ID);
		AjaxComet as = null;

		if (sid != null) {// client connected before,they has a session id.
			SessionI s = this.sessions.touchSession(sid);
			if (s == null) {// session is missing,session timeout?
				//
			} else {//found session,ok.
				as = (AjaxComet) s.getAttribute(SK_COMET, true);
			}
		}

		// NOTE, the timeout for first message should be long enough,
		//
		// it's rely on the client's applevel to keep the connection open, then
		// it will send applevel's heartbeat.
		//
		AjaxRequestContext arc = new AjaxRequestContext(this.maxIdleTimeout, this.timeoutForFirstMessage, as,
				req, res);
		// NOTE write to response will cause the EOF of the reader?
		if (as != null) {
			as.startRequest(arc);// may blocking if has old request .
		}
		try {

			arc.writeMessageStart();
			try {
				this.doRequest(sid, req, arc);

				arc.tryFetchMessage();

			} finally {
				arc.writeMessageEnd();
			}

		} finally {

			if (as != null) {
				as.endRequest();
			}

		}
	}

	protected void doRequest(String sid, HttpRequestI req, AjaxRequestContext arc) {
		// virtual terminal id
		if (sid != null && arc.as == null) {// session is required, but timeout or invalide session id.
			//
			arc.writeError(AjaxMsg.ERROR_CODE_SESSION_NOTFOUND, "yes,not found session with id:" + sid);
			return;
		}

		Reader reader = req.getContentAsReader();
		List<AjaxMsg> amL = AjaxMsg.tryParseMsgArray(reader);

		String firstSid = null;
		int i = 0;
		int total = amL.size();
		for (AjaxMsg am : amL) {

			Path path = am.getPath();

			AjaxMsgHandler hdl = this.handlers.get(path);
			if (hdl == null) {
				hdl = this.defaultAjaxMsgHandler;
			}
			if (LOG.isDebugEnabled()) {
				LOG.debug("resolved ajax handler:" + hdl + " for ajax msg:" + am);
			}

			AjaxMsgContext amc = new AjaxMsgContext(i, total, am, arc);

			hdl.handle(amc);
			i++;
		}

	}

}
