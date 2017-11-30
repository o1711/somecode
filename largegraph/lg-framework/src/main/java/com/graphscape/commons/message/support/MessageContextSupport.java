/**
 * Dec 8, 2013
 */
package com.graphscape.commons.message.support;

import com.graphscape.commons.handling.spi.HandlingProviderI;
import com.graphscape.commons.handling.support.HandlingContextSupport;
import com.graphscape.commons.lang.ErrorInfos;
import com.graphscape.commons.lang.HasPathI;
import com.graphscape.commons.lang.ResolverI;
import com.graphscape.commons.message.MessageI;
import com.graphscape.commons.message.MessageWrapper;
import com.graphscape.commons.message.provider.default_.DefaultMessage;
import com.graphscape.commons.util.Path;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class MessageContextSupport extends HandlingContextSupport<MessageI, MessageI> implements HasPathI {

	private MessageWrapper wrapper;

	private ResolverI<MessageI, ?> messageWrapperResolver;

	public MessageContextSupport(MessageI msg, HandlingProviderI<MessageI, MessageI, ?> mspi) {
		super(msg, new DefaultMessage(msg));
		this.messageWrapperResolver = mspi.getInputDataWrapperResolver();

	}

	public <T extends MessageWrapper> T getEvent() {
		if (this.wrapper != null) {
			return (T) this.wrapper;
		}

		this.wrapper = (MessageWrapper) this.messageWrapperResolver.resolve(this.input);
		return (T) this.wrapper;

	}

	@Override
	public Path getPath() {
		return this.getInputData().getPath();
	}

	@Override
	public ErrorInfos getErrorInfos() {
		return this.getOutputData().getErrorInfos();
	}

}
