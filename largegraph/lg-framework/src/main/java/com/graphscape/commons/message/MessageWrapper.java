/**
 *  
 */
package com.graphscape.commons.message;

import com.graphscape.commons.message.provider.default_.DefaultMessage;
import com.graphscape.commons.message.support.ProxyMessage;
import com.graphscape.commons.util.Path;

/**
 * Top class of event.
 * 
 * @author wu
 * 
 */
public class MessageWrapper extends ProxyMessage {

	public static final String HK_WRAPPER_CLASS = "_wrapperClass";// TODO event
																	// type?

	public MessageWrapper(Path path) {
		this(path, null);
	}

	public MessageWrapper(MessageI src, Path path) {
		this(src, path, null);
	}

	public MessageWrapper(Path path, String id) {
		this(null, path, id);
	}

	public MessageWrapper(MessageI src, Path path, String id) {
		super(new DefaultMessage(src, path, id));

		this.setHeader(HK_WRAPPER_CLASS, this.getClass().getName());//
	}

	public MessageWrapper(MessageI tgt) {
		super(tgt);
	}

}
