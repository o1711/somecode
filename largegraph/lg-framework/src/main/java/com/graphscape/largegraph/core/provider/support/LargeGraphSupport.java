/**
 * 2013 ����11:15:34
 */
package com.graphscape.largegraph.core.provider.support;

import com.graphscape.commons.concurrent.FutureI;
import com.graphscape.commons.configuration.ConfigurableI;
import com.graphscape.commons.configuration.ConfigurationI;
import com.graphscape.commons.handling.ProcessorI;
import com.graphscape.commons.handling.provider.default_.DefaultMessageProcessor;
import com.graphscape.commons.handling.spi.HandlingProviderI;
import com.graphscape.commons.lang.provider.default_.DefaultProperties;
import com.graphscape.commons.lang.support.LifeCycleSupport;
import com.graphscape.commons.message.MessageI;
import com.graphscape.largegraph.core.EventContext;
import com.graphscape.largegraph.core.GraphI;
import com.graphscape.largegraph.core.LargeGraphI;
import com.graphscape.largegraph.core.VertexI;
import com.graphscape.largegraph.core.provider.blueprint.DefaultMessageServiceProvider;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public abstract class LargeGraphSupport extends LifeCycleSupport implements ConfigurableI, LargeGraphI {

	protected ConfigurationI config;

	protected ProcessorI<MessageI, MessageI> messageProcessor;

	protected HandlingProviderI<MessageI, MessageI, EventContext> messageServiceProvider;

	public LargeGraphSupport() {
		this.messageServiceProvider = new DefaultMessageServiceProvider(this);

		this.messageProcessor = new DefaultMessageProcessor(this.messageServiceProvider);

	}

	public void config(ConfigurationI cfg) {
		this.config = cfg;
	}

	@Override
	public ConfigurationI getConfiguration() {
		return this.config;
	}

	@Override
	public FutureI<MessageI> putMessage(MessageI msg) {
		FutureI<MessageI> rt = this.messageProcessor.process(msg);

		return rt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.lang.LifeCycleI#start()
	 */

	@Override
	public void doStart() {
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

	/**
	 * @return the messageProcessor
	 */
	@Override
	public ProcessorI<MessageI, MessageI> getMessageProcessor() {
		return messageProcessor;
	}

	@Override
	public VertexI getVertex(String id) {

		return getVertex(id, false);
	}

	@Override
	public VertexI addVertex() {
		return this.addVertex(new DefaultProperties<Object>());
	}

}
