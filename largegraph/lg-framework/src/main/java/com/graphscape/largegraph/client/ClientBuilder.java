/**
 * Dec 16, 2013
 */
package com.graphscape.largegraph.client;

import java.net.URI;
import java.net.URISyntaxException;

import com.graphscape.commons.client.MessageClientServiceProviderI;
import com.graphscape.commons.handling.support.CollectionListener;
import com.graphscape.commons.lang.Builder;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.lang.HandlerI;
import com.graphscape.commons.message.MessageI;
import com.graphscape.commons.util.TimeAndUnit;
import com.graphscape.largegraph.client.provider.default_.DefaultClient;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class ClientBuilder extends Builder<ClientI> {

	MessageClientServiceProviderI spi;

	URI uri = null;

	CollectionListener<MessageI> callback = new CollectionListener<MessageI>();
	
	private String credentical;

	public ClientBuilder() {

	}

	public ClientBuilder handler(HandlerI<MessageI> hdl) {
		this.callback.addHandler(hdl);
		return this;
	}

	public ClientBuilder uri(String uriS) {
		URI uri;
		try {
			uri = new URI(uriS);
		} catch (URISyntaxException e) {
			throw GsException.toRuntimeException(e);
		}
		return this.uri(uri);
	}

	public ClientBuilder uri(URI uri) {
		this.uri = uri;
		return this;
	}

	public ClientI buildAndConnect(TimeAndUnit timeout) {
		ClientI rt = this.build();
		rt.connect(this.credentical).get(timeout);
		return rt;
	}

	@Override
	public ClientI build() {

		DefaultClient rt = new DefaultClient(this.uri, this.callback);

		return rt;
	}
}
