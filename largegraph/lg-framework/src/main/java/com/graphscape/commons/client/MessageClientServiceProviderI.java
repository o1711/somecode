/**
 * Dec 13, 2013
 */
package com.graphscape.commons.client;

import java.net.URI;

import com.graphscape.commons.lang.HandlerI;
import com.graphscape.commons.lang.ServiceProviderI;
import com.graphscape.commons.marshal.MarshallerI;
import com.graphscape.commons.message.MessageI;
import com.graphscape.commons.util.Path;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public interface MessageClientServiceProviderI extends ServiceProviderI {

	public MarshallerI getMarshaller();

	public HandlerI<MessageI> getMessageCallback();

	public URI getUri();

	public <T extends MessageClientI> T newClientWrapper(MessageClientI mc);

	public Path getHeartBeatMessagePath();

	public boolean isRequestMessageFormatPrettyPrint();
}
