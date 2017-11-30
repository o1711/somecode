/**
 * Dec 15, 2013
 */
package com.graphscape.largegraph.web.provider.default_;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.graphscape.commons.comet.CometI;
import com.graphscape.commons.comet.support.AbstractCometListener;
import com.graphscape.commons.concurrent.FiniteBlockingQueueI;
import com.graphscape.commons.concurrent.FutureI;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.lang.WithIdI;
import com.graphscape.commons.marshal.MarshallerI;
import com.graphscape.commons.marshal.MarshallingProviderI;
import com.graphscape.commons.marshal.provider.default_.SimpleJsonMarshallingProvider;
import com.graphscape.commons.message.MessageI;
import com.graphscape.commons.util.TimeAndUnit;
import com.graphscape.largegraph.core.GraphI;
import com.graphscape.largegraph.server.LargeGraphServerI;
import com.graphscape.largegraph.server.MessageQueueManagerI;
import com.graphscape.largegraph.server.SessionContext;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class LargeGraphCometListener extends AbstractCometListener {

	private static final Logger LOG = LoggerFactory.getLogger(LargeGraphCometListener.class);

	public static final String SK_SESSION_CONTEXT = "_sessionContext";

	private LargeGraphServerI lgs;

	private MarshallerI marshaller;

	private TimeAndUnit timeout = TimeAndUnit.parse("10M");// TODO configurable?

	private MessageQueueManagerI queueManager;

	public LargeGraphCometListener(LargeGraphServerI lgs, MessageQueueManagerI qm) {
		this.lgs = lgs;
		this.queueManager = qm;
		MarshallingProviderI sf = new SimpleJsonMarshallingProvider();
		this.marshaller = sf.getMarshaller(MessageI.class, true);
	}

	@Override
	public void onConnect(CometI ws) {
		// comet connected,
		// then connect comet with server:by sharing the same session
		// object,save queue Id in session;so the server side handler can put
		// message to the session's message queue.

		WithIdI<FiniteBlockingQueueI<MessageI>> mq = this.queueManager.createMember();
		String gid = ws.getAttribute("graphId", true);

		GraphI g = this.lgs.getLargeGraph().getGraph(gid);
		SessionContext sc = new SessionContext(g, mq.getTarget());
		ws.setAttribute(SK_SESSION_CONTEXT, sc);
		// TODO not use this way, a new thread is occupied.
		OutputQueueWatchingTask worker = new OutputQueueWatchingTask(ws, mq.getTarget(), this.marshaller);
		sc.setAttribute("WORKER", worker);
		worker.submit();
	}

	public void sendMessage(CometI c, MessageI msg) {
		Object encode = this.marshaller.marshal(msg);
		c.sendMessage(encode);
	}

	@Override
	public void onMessage(CometI ws, Object ms) {
		SessionContext sc = ws.getAttribute(SK_SESSION_CONTEXT, true);
		MessageI msg = (MessageI) this.marshaller.unmarshal(ms);
		msg.setAttribute("sessionContext", sc);//
		//
		FutureI<MessageI> res = this.lgs.putMessage(msg);

		// if is sync message, then wait the response.
		// else,aysnc message not wait the response, the response should be in a
		// queue
		try {

			MessageI rtMsg = res.get(this.timeout);
			Object resC = this.marshaller.marshal(rtMsg);

			ws.sendMessage(resC);

		} catch (Exception e) {
			throw GsException.toRuntimeException(e);
		}

	}

	@Override
	public void onException(CometI ws, Throwable t) {

		LOG.error("comet exception,id:" + ws.getId(), t);

	}

	@Override
	public void onClose(CometI ws, int statusCode, String reason) {
		LOG.info("comet closed,id:" + ws.getId() + ",statusCode:" + statusCode + ",reaons:" + reason);
		SessionContext sc = ws.getAttribute(SK_SESSION_CONTEXT, true);
		OutputQueueWatchingTask worker = (OutputQueueWatchingTask) sc.getAttribute("WORKER", true);
		worker.close();
	}

}
