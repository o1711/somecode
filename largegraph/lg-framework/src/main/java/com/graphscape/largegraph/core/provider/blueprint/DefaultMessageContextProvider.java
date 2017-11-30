/**
 * Dec 8, 2013
 */
package com.graphscape.largegraph.core.provider.blueprint;

import com.graphscape.commons.handling.spi.HandlingProviderI;
import com.graphscape.commons.lang.ResolverI;
import com.graphscape.commons.message.MessageI;
import com.graphscape.largegraph.core.EventContext;
import com.graphscape.largegraph.core.GraphI;
import com.graphscape.largegraph.core.LargeGraphI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class DefaultMessageContextProvider implements ResolverI<MessageI, EventContext> {

	private LargeGraphI lg;

	private HandlingProviderI<MessageI, MessageI, EventContext> mspi;

	public DefaultMessageContextProvider(LargeGraphI lg,
			HandlingProviderI<MessageI, MessageI, EventContext> mspi) {
		this.lg = lg;
		this.mspi = mspi;
	}

	@Override
	public EventContext resolve(MessageI msg) {
		return new EventContext(msg, this.mspi, lg);
	}

}
