/**
 * Dec 15, 2013
 */
package com.graphscape.largegraph.web.provider.default_;

import java.util.NoSuchElementException;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.graphscape.commons.comet.CometI;
import com.graphscape.commons.concurrent.FiniteBlockingQueueI;
import com.graphscape.commons.lang.support.ThreadWorkerSupport;
import com.graphscape.commons.marshal.MarshallerI;
import com.graphscape.commons.message.MessageI;
import com.graphscape.commons.util.TimeAndUnit;

/**
 * // TODO share a task scheduler.
 * 
 * @author wuzhen0808@gmail.com TODO use WorkerI and FiniteBlockingQueueI
 */
public class OutputQueueWatchingTask extends ThreadWorkerSupport<Integer> {
	private static final Logger LOG = LoggerFactory.getLogger(OutputQueueWatchingTask.class);
	public CometI comet;
	private MarshallerI mesageSerializer;
	private FiniteBlockingQueueI<MessageI> queue;
	private TimeAndUnit timeout = TimeAndUnit.parse("10S");

	public OutputQueueWatchingTask(CometI comet, FiniteBlockingQueueI<MessageI> mq, MarshallerI mesageSerializer) {
		this.mesageSerializer = mesageSerializer;
		this.comet = comet;
		this.queue = mq;
		this.executor = Executors.newSingleThreadExecutor();// message consumer.

	}

	@Override
	public Integer call() {

		while (true) {
			try {
				MessageI msg = this.queue.poll(this.timeout);
				if (msg == null) {
					continue;
				}
				this.onMessage(msg);
			} catch (NoSuchElementException e) {
				break;
			}
		}
		LOG.info("Comet message transfer is stop working");
		return null;
	}

	private void onMessage(MessageI t) {
		String msgS = (String) this.mesageSerializer.marshal(t);
		// String msg = JsonElement.toJSONString(js);
		this.comet.sendMessage(msgS);
	}
	
	public void close(){
		this.queue.close();
	}

}
