/**
 * Dec 16, 2013
 */
package com.graphscape.largegraph.client.provider.default_;

import java.net.URI;

import com.graphscape.commons.client.MessageClientI;
import com.graphscape.commons.lang.HandlerI;
import com.graphscape.commons.message.MessageI;
import com.graphscape.commons.util.Path;
import com.graphscape.largegraph.client.support.MessageClientServiceProviderSupport;

public class DefaultMessageClientProvider extends MessageClientServiceProviderSupport {

	/**
	 * @param uri
	 * @param cb
	 */
	public DefaultMessageClientProvider(URI uri, HandlerI<MessageI> hdl) {
		super(false, uri, hdl);
	}

	@Override
	public <T extends MessageClientI> T newClientWrapper(MessageClientI mc) {
		return (T) new DefaultClient(mc);
	}

	@Override
	public Path getHeartBeatMessagePath() {
		return null;
	}

	@Override
	public boolean isRequestMessageFormatPrettyPrint() {
		return false;
	}

}