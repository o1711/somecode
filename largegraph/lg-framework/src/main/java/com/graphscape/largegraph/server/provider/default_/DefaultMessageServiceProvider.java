/**
 * Dec 9, 2013
 */
package com.graphscape.largegraph.server.provider.default_;

import com.graphscape.commons.handling.HandlerResolverI;
import com.graphscape.commons.handling.spi.HandlingProviderI;
import com.graphscape.commons.lang.ResolverI;
import com.graphscape.commons.message.MessageI;
import com.graphscape.commons.message.provider.default_.SimpleMessageWrapperResolver;
import com.graphscape.commons.message.spi.support.MessageServiceProviderSupport;
import com.graphscape.commons.session.SessionManagerI;
import com.graphscape.commons.util.Path;
import com.graphscape.largegraph.server.LargeGraphServerI;
import com.graphscape.largegraph.server.Request;
import com.graphscape.largegraph.server.RequestContext;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class DefaultMessageServiceProvider extends MessageServiceProviderSupport<RequestContext> implements
		HandlingProviderI<MessageI, MessageI, RequestContext> {

	protected ResolverI<MessageI, RequestContext> messageContextProvider;
	protected HandlerResolverI<MessageI, MessageI, RequestContext> messageHandlerResolver;
	protected ResolverI<MessageI, ?> messageWrapperResolver;
	protected SessionManagerI sessionManager;

	public DefaultMessageServiceProvider(SessionManagerI sm, LargeGraphServerI lgs,
			HandlerResolverI<MessageI, MessageI, RequestContext> mhr) {
		this.sessionManager = sm;
		messageContextProvider = new DefaultMessageContextProvider(sm, lgs.getLargeGraph(), this);
		messageHandlerResolver = mhr;
		this.messageWrapperResolver = new SimpleMessageWrapperResolver().defaultEventClass(Request.class)
				.addEventType(Path.valueOf("/lg"), Request.class);

	}

	@Override
	public ResolverI<MessageI, RequestContext> getHandlingContextResolver() {
		return this.messageContextProvider;
	}

	@Override
	public HandlerResolverI<MessageI, MessageI, RequestContext> getHandlerResolver() {
		return this.messageHandlerResolver;
	}

	@Override
	public ResolverI<MessageI, ?> getInputDataWrapperResolver() {

		return this.messageWrapperResolver;

	}
}
