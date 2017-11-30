/**
 * Dec 9, 2013
 */
package com.graphscape.largegraph.core.provider.blueprint;

import com.graphscape.commons.handling.HandlerResolverI;
import com.graphscape.commons.handling.spi.HandlingProviderI;
import com.graphscape.commons.lang.ResolverI;
import com.graphscape.commons.message.MessageI;
import com.graphscape.commons.message.spi.support.MessageServiceProviderSupport;
import com.graphscape.largegraph.core.EventContext;
import com.graphscape.largegraph.core.LargeGraphI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class DefaultMessageServiceProvider extends MessageServiceProviderSupport<EventContext> implements
		HandlingProviderI<MessageI, MessageI, EventContext> {

	protected ResolverI<MessageI, EventContext> messageContextProvider;
	protected HandlerResolverI<MessageI, MessageI, EventContext> messageHandlerResolver;
	protected ResolverI<MessageI, ?> messageWrapperResolver;

	public DefaultMessageServiceProvider(LargeGraphI lg) {
		messageContextProvider = new DefaultMessageContextProvider(lg, this);
		messageHandlerResolver = new DefaultMessageHandlerResolver();
		this.messageWrapperResolver = new LgMessageWrapperResolver();
	}

	@Override
	public ResolverI<MessageI, EventContext> getHandlingContextResolver() {
		return this.messageContextProvider;
	}

	@Override
	public HandlerResolverI<MessageI, MessageI, EventContext> getHandlerResolver() {
		return this.messageHandlerResolver;
	}

	@Override
	public ResolverI<MessageI, ?> getInputDataWrapperResolver() {

		return this.messageWrapperResolver;

	}
}
