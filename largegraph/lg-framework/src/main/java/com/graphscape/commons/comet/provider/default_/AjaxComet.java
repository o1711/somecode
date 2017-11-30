/**
 *  
 */
package com.graphscape.commons.comet.provider.default_;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.graphscape.commons.comet.CometListenerI;
import com.graphscape.commons.comet.support.CometSupport;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.lang.PropertiesI;

/**
 * @author wu
 *         <p>
 *         The session of client connection.
 */
public class AjaxComet extends CometSupport {

	private static final Logger LOG = LoggerFactory.getLogger(AjaxComet.class);

	private BlockingQueue<AjaxMsg> queue;

	private long timeoutMs = 1000;

	// public List<MessageQueueI> providerMessageQueueList = new
	// ArrayList<MessageQueueI>();

	// only one request exist for same time for processing,terminate the old
	// one for new one arrive.

	private AjaxRequestContext theRequestContext;

	private Object requestLock = new Object();
	

	public AjaxRequestContext getTheRequestContext() {
		return theRequestContext;
	}

	public void startRequest(AjaxRequestContext theRequestContext) {
		synchronized (this.requestLock) {

			boolean hasold = this.theRequestContext != null;
			if (hasold) {
				// interrupt message will cause the current request finish
				if (LOG.isDebugEnabled()) {
					LOG.debug("interrupt and waiting the old request to be finished...");
				}

				this.putAjaxMessage(AjaxMsg.interruptMsg());//
			}

			// wait the current request finish,if it has not yet..
			while (this.theRequestContext != null) {// wait the old one finshed
				try {
					this.requestLock.wait();
				} catch (InterruptedException e) {

				}
			}
			if (hasold) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("the old request is finished.");
				}
			}
			this.theRequestContext = theRequestContext;
		}

	}

	public void endRequest() {
		synchronized (this.requestLock) {
			this.theRequestContext = null;
			this.requestLock.notifyAll();
		}
	}

	public AjaxComet(String tid, String creditial) {
		super("ajax", tid,creditial);
		this.queue = new LinkedBlockingQueue<AjaxMsg>();
		
	}

	@Override
	public void sendMessage(Object msg) {

		AjaxMsg am = new AjaxMsg(AjaxMsg.MESSAGE);

		am.setProperty(AjaxMsg.PK_CONTENT, msg);

		this.putAjaxMessage(am);
	}

	public BlockingQueue<AjaxMsg> getQueue() {
		return queue;
	}

	public void putAjaxMessage(AjaxMsg am) {
		//
		try {
			this.queue.put(am);
		} catch (InterruptedException e) {
			throw new GsException(e);
		}//
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.commons.websocket.api.WebSocketI#addListener(com.fs.commons.websocket
	 * .api.WsListenerI )
	 */
	@Override
	public void addListener(CometListenerI ln) {
		this.addListener(ln);
	}

	/**
	 * @return the timeoutMs
	 */
	public long getTimeoutMs() {
		return timeoutMs;
	}

}
