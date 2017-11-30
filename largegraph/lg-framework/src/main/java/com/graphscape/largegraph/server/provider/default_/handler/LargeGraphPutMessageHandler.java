/**
 * Dec 16, 2013
 */
package com.graphscape.largegraph.server.provider.default_.handler;

import com.graphscape.commons.concurrent.FutureI;
import com.graphscape.commons.message.MessageI;
import com.graphscape.commons.util.TimeAndUnit;
import com.graphscape.largegraph.server.Request;
import com.graphscape.largegraph.server.RequestContext;
import com.graphscape.largegraph.server.support.RequestHandlerSupport;

/**
 * @author wuzhen0808@gmail.com
 *         <p>
 *         Synchronized response
 */
public class LargeGraphPutMessageHandler extends RequestHandlerSupport {

	private TimeAndUnit timeout = TimeAndUnit.parse("10M");// TODO configurable

	@Override
	protected void handleInternal(RequestContext mc) {

		Request se = mc.getRequest();
		// NOTE, the sub message carried out by the outer message
		MessageI msg = (MessageI) se.getPayload("message", true);
		MessageI rt = mc.getOutputData();

		FutureI<MessageI> rf = mc.getLargeGraph().putMessage(msg);
		MessageI res = rf.get(timeout);// exception will add to response by
										// framework code.
		rt.setPayload("message", res);

	}
}
