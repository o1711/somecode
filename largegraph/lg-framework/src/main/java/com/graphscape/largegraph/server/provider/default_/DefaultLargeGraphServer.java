/**
 * Dec 7, 2013
 */
package com.graphscape.largegraph.server.provider.default_;

import com.graphscape.commons.concurrent.FutureI;
import com.graphscape.commons.handling.HandlerResolverI;
import com.graphscape.commons.handling.ProcessorI;
import com.graphscape.commons.handling.provider.default_.DefaultMessageProcessor;
import com.graphscape.commons.handling.spi.HandlingProviderI;
import com.graphscape.commons.lang.PropertiesI;
import com.graphscape.commons.lang.provider.default_.DefaultProperties;
import com.graphscape.commons.message.MessageI;
import com.graphscape.commons.session.SessionManagerI;
import com.graphscape.largegraph.core.GraphI;
import com.graphscape.largegraph.core.LargeGraphI;
import com.graphscape.largegraph.server.MessageQueueManagerI;
import com.graphscape.largegraph.server.RequestContext;
import com.graphscape.largegraph.server.support.LargeGraphServerSupport;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class DefaultLargeGraphServer extends LargeGraphServerSupport {
	protected ProcessorI<MessageI, MessageI> messageProcessor;

	protected HandlingProviderI<MessageI, MessageI, RequestContext> messageServiceProvider;

	public DefaultLargeGraphServer(LargeGraphI lg, SessionManagerI sm,
			HandlerResolverI<MessageI, MessageI, RequestContext> mhr) {
		this(lg, sm, null, mhr);
	}

	private DefaultLargeGraphServer(LargeGraphI lg, SessionManagerI sm, MessageQueueManagerI qm,
			HandlerResolverI<MessageI, MessageI, RequestContext> mhr) {
		super(lg, sm, qm);
		this.messageServiceProvider = new DefaultMessageServiceProvider(this.sessionManager, this, mhr);

		this.messageProcessor = new DefaultMessageProcessor<MessageI, MessageI, RequestContext>(
				this.messageServiceProvider);
	}

	@Override
	public void doStart() {
		// create a root graph, to allow user login and perform operation.

		this.messageProcessor.start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.lang.LifeCycleI#shutdown()
	 */
	@Override
	public void doShutdown() {
		this.messageProcessor.shutdown();
	}

	@Override
	public FutureI<MessageI> putMessage(MessageI msg) {
		return this.messageProcessor.process(msg);
	}

}
