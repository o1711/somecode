package org.ta.common.scheduler;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import org.ta.common.lifecycle.TaLifeCycle;
import org.ta.common.util.TaTimeSpan;

public interface TaScheduler extends TaLifeCycle {

	public void addListener(String trigger, TaSchdulerListener listener);

	public void addQueue(String trigger,
			BlockingQueue<Map<String, Object>> quque);

	public void schedule(String trigger, Date start, TaTimeSpan interval,
			Map<String, Object> data);

}
