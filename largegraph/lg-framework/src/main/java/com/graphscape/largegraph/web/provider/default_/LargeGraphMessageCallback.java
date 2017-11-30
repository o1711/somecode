/**
 * Dec 14, 2013
 */
package com.graphscape.largegraph.web.provider.default_;

import com.graphscape.commons.concurrent.FutureI;
import com.graphscape.commons.lang.CallbackI;
import com.graphscape.commons.message.MessageI;
import com.graphscape.commons.util.TimeAndUnit;
import com.graphscape.largegraph.server.LargeGraphServerI;

/**
 * Stateless message port.
 * 
 * @author wuzhen0808@gmail.com
 * 
 */
public class LargeGraphMessageCallback implements CallbackI<MessageI, MessageI> {

	protected LargeGraphServerI mprocessor;

	protected TimeAndUnit timeout;

	public LargeGraphMessageCallback(LargeGraphServerI mprocessor, TimeAndUnit timeout) {
		this.mprocessor = mprocessor;
		this.timeout = timeout;
	}

	@Override
	public MessageI execute(MessageI t) {

		FutureI<MessageI> f = this.mprocessor.putMessage(t);
		MessageI rt = f.get(timeout);
		return rt;

	}

}
