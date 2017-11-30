/**
 * Dec 16, 2013
 */
package com.graphscape.largegraph.client.support;

import java.net.URI;

import com.graphscape.commons.client.MessageClientServiceProviderI;
import com.graphscape.commons.lang.HandlerI;
import com.graphscape.commons.lang.support.ServiceProviderSupport;
import com.graphscape.commons.marshal.MarshallerI;
import com.graphscape.commons.marshal.provider.default_.SimpleJsonMarshallingProvider;
import com.graphscape.commons.message.MessageI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public abstract class MessageClientServiceProviderSupport extends ServiceProviderSupport implements
		MessageClientServiceProviderI {
	protected MarshallerI serrializer;
	protected URI uri;
	protected HandlerI<MessageI> cb;

	public MessageClientServiceProviderSupport(boolean debug, URI uri, HandlerI<MessageI> cb) {
		this(debug, uri, cb, null);
	}

	public MessageClientServiceProviderSupport(boolean debug, URI uri, HandlerI<MessageI> cb, MarshallerI ser) {
		super(debug);
		this.uri = uri;
		this.cb = cb;
		this.serrializer = ser;
		if (this.serrializer == null) {
			this.serrializer = new SimpleJsonMarshallingProvider().getMarshaller(MessageI.class, true);

		}
	}

	@Override
	public MarshallerI getMarshaller() {
		return this.serrializer;
	}

	@Override
	public HandlerI<MessageI> getMessageCallback() {
		return this.cb;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.client.MessageClientServiceProviderI#getUri()
	 */
	@Override
	public URI getUri() {
		return this.uri;
	}

}
