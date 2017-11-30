/**
 * Dec 16, 2013
 */
package com.graphscape.largegraph.server;

import com.graphscape.commons.message.MessageI;
import com.graphscape.commons.message.MessageWrapper;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class Request extends MessageWrapper {

	public static final String HK_IS_ASYNC = "_is_async";

	/**
	 * @param tgt
	 */
	public Request(MessageI tgt) {
		super(tgt);
	}

	/**
	 * @return
	 */
	public boolean isAsync() {

		return Boolean.valueOf(this.getHeader(HK_IS_ASYNC, "false"));
	}

}
