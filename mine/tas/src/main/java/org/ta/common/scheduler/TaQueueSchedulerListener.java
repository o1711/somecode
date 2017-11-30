package org.ta.common.scheduler;

import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class TaQueueSchedulerListener implements TaSchdulerListener {
	BlockingQueue<Map<String, Object>> queue;

	public TaQueueSchedulerListener(BlockingQueue<Map<String, Object>> quque) {
		this.queue = quque;
	}

	@Override
	public void onTriggered(String trigger, Map<String, Object> triggerContext) {
		this.queue.offer(triggerContext);
	}

}
