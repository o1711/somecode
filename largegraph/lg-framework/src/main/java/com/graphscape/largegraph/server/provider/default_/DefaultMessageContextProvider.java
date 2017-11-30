/**
 * Dec 8, 2013
 */
package com.graphscape.largegraph.server.provider.default_;

import com.graphscape.commons.handling.spi.HandlingProviderI;
import com.graphscape.commons.lang.ResolverI;
import com.graphscape.commons.message.MessageI;
import com.graphscape.commons.session.SessionManagerI;
import com.graphscape.largegraph.core.LargeGraphI;
import com.graphscape.largegraph.server.RequestContext;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class DefaultMessageContextProvider implements ResolverI<MessageI, RequestContext> {

	private LargeGraphI lg;
	private HandlingProviderI<MessageI, MessageI, RequestContext> mspi;
	
	private SessionManagerI sessionManager;

	public DefaultMessageContextProvider(SessionManagerI sm,LargeGraphI lg,
			HandlingProviderI<MessageI, MessageI, RequestContext> mspi) {
		this.sessionManager = sm;
		this.lg = lg;
		this.mspi = mspi;
	}

	@Override
	public RequestContext resolve(MessageI msg) {		
		return new RequestContext(this.sessionManager,msg, this.mspi, lg);
	}

}
