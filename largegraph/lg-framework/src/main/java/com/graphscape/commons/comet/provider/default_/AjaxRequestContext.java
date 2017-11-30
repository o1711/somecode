/**
 * All right is from Author of the file,to be explained in comming days.
 * May 8, 2013
 */
package com.graphscape.commons.comet.provider.default_;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.graphscape.commons.http.HttpRequestI;
import com.graphscape.commons.http.HttpResponseI;
import com.graphscape.commons.util.TimeAndUnit;

/**
 * @author wu
 * 
 */
public class AjaxRequestContext {

	public static final Logger LOG = LoggerFactory.getLogger(AjaxRequestContext.class);

	@Deprecated
	// move to servlet.
	public TimeAndUnit timeoutForSession;

	public TimeAndUnit timeoutForFirstMessage;// 2 mins.

	public TimeAndUnit timeoutForMoreMessage = TimeAndUnit.valueOf(1, TimeUnit.MILLISECONDS);// should
																								// be
																								// short
																								// enough.

	public HttpRequestI req;

	public HttpResponseI res;

	public int totalMessages = 0;

	public AjaxComet as;

	protected static JsonParser parser = new JsonParser();

	protected static Gson gson = new GsonBuilder().setPrettyPrinting().create();

	/**
	 * @param req2
	 * @param res2
	 */
	public AjaxRequestContext(TimeAndUnit timeoutForSession, TimeAndUnit timeoutForFirstMessage,
			AjaxComet as, HttpRequestI req, HttpResponseI res2) {
		this.req = req;
		this.res = res2;
		this.as = as;
		this.timeoutForSession = timeoutForSession;
		this.timeoutForFirstMessage = timeoutForFirstMessage;
	}

	/**
	 * May 13, 2013
	 */
	public void writeCloseSuccess() {
		//
		AjaxMsg msg = new AjaxMsg(AjaxMsg.CLOSE.getSubPath("success"));
		this.write(msg);
	}

	public void writeError(String code, String msg) {
		AjaxMsg am = new AjaxMsg(AjaxMsg.ERROR);
		am.setProperty(AjaxMsg.PK_ERROR_CODE, code);
		am.setProperty(AjaxMsg.PK_ERROR_MSG, msg);
		this.write(am);
	}

	/**
	 *
	 */
	public void write(AjaxMsg msg) {
		if (LOG.isTraceEnabled()) {
			LOG.trace("write ajax msg to client,msg:" + msg);
		}

		if (this.totalMessages > 0) {
			this.res.write(",");
		}
		JsonObject json = msg.toJsonObject();
		String js = gson.toJson(json);

		this.res.write(js);

		this.totalMessages++;

	}

	public void tryFetchMessage() {

		if (this.as == null) {
			//no comet established,for this request,so response immediately
			// make the response.
			if (LOG.isDebugEnabled()) {
				LOG.debug("no comet,what's happen");
			}

			return;
		}
		// wait a long time for the first message
		TimeAndUnit timeout = this.timeoutForFirstMessage;
		while (true) {

			if (this.totalMessages > 0) {
				// wait a short time for more messages.
				timeout = this.timeoutForMoreMessage;//
			}
			AjaxMsg msg = null;
			if (LOG.isTraceEnabled()) {
				LOG.trace("polling next message from queue,timeout:" + timeout);

			}
			try {
				msg = this.as.getQueue().poll(timeout.getValue(), timeout.getUnit());
			} catch (InterruptedException e) {

			}

			if (msg == null) {
				// timeout to get the new message from queue of session.
				if (LOG.isDebugEnabled()) {

					LOG.debug("finishing response, for timeout to get the new message from queue of session.");

				}
				break;
			}

			// control message got
			if (msg.isInterruptMsg()) {
				// more message
				// any way, break and make the response.
				if (LOG.isDebugEnabled()) {
					LOG.debug("finishing response,for interrupt msg got.");
				}

				break;
			}

			// write user's layer message to response
			this.write(msg);

		}

	}

	public void writeMessageStart() {

		this.res.write("[");
	}

	public void writeMessageEnd() {
		this.res.write("]");
		// flush?
	}

}
