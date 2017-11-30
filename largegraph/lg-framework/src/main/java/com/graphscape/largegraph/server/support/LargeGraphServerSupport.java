/**
 * Dec 15, 2013
 */
package com.graphscape.largegraph.server.support;

import com.graphscape.commons.lang.support.ConfigurableLifeCycleSupport;
import com.graphscape.commons.session.SessionManagerI;
import com.graphscape.largegraph.core.LargeGraphI;
import com.graphscape.largegraph.server.LargeGraphServerI;
import com.graphscape.largegraph.server.MessageQueueManagerI;
import com.graphscape.largegraph.server.provider.default_.DefaultMessageQueueBuilder;
import com.graphscape.largegraph.server.provider.default_.DefaultMessageQueueManager;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public abstract class LargeGraphServerSupport extends ConfigurableLifeCycleSupport implements
		LargeGraphServerI {

	protected SessionManagerI sessionManager;

	protected MessageQueueManagerI messageQueueManager;

	protected LargeGraphI largeGraph;

	public LargeGraphServerSupport(LargeGraphI lg, SessionManagerI sm) {
		this(lg, sm, null);
	}

	public LargeGraphServerSupport(LargeGraphI lg, SessionManagerI sm, MessageQueueManagerI qm) {
		this.largeGraph = lg;
		this.sessionManager = sm;
		this.messageQueueManager = qm;
		if (this.messageQueueManager == null) {

			this.messageQueueManager = new DefaultMessageQueueManager(new DefaultMessageQueueBuilder());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.largegraph.server.LargeGraphServerI#getLargeGraph()
	 */
	@Override
	public LargeGraphI getLargeGraph() {
		return this.largeGraph;
	}
}
