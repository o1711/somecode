/**
 * Dec 16, 2013
 */
package com.graphscape.largegraph.server;

import com.graphscape.commons.handling.spi.HandlingProviderI;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.message.MessageI;
import com.graphscape.commons.message.support.MessageContextSupport;
import com.graphscape.commons.session.SessionI;
import com.graphscape.commons.session.SessionManagerI;
import com.graphscape.largegraph.core.GraphI;
import com.graphscape.largegraph.core.LargeGraphI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class RequestContext extends MessageContextSupport {

	private SessionManagerI sessionManager;
	private SessionContext sessionContext;
	private LargeGraphI largeGraph;

	/**
	 * @param msg
	 * @param mspi
	 */
	public RequestContext(SessionManagerI sm, MessageI msg, HandlingProviderI<MessageI, MessageI, ?> mspi,
			LargeGraphI lg) {
		super(msg, mspi);
		this.sessionManager = sm;
		this.largeGraph = lg;
	}

	public Request getRequest() {
		return this.getEvent();
	}

	public boolean isAsync() {
		return this.getRequest().isAsync();
	}

	public SessionContext getSessionContext(boolean force) {
		if (this.sessionContext != null) {
			return this.sessionContext;
		}
		this.sessionContext = this.getInputData().getAttribute("sessionContext",false);

		if (this.sessionContext == null && force) {
			throw new GsException("no session binding to request message:" + this.input.getId() + ",detail:"
					+ this.input);
		}
		return this.sessionContext;
	}

	public LargeGraphI getLargeGraph() {
		return largeGraph;
	}

	public GraphI getGraph() {
		this.getSessionContext(true);//
		return this.sessionContext.getGraph();

	}

}
