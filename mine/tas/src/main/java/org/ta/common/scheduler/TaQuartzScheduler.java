package org.ta.common.scheduler;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.ta.common.config.TaException;
import org.ta.common.util.TaTimeSpan;

public class TaQuartzScheduler implements TaScheduler {
	private static class TheJob implements Job {

		@Override
		public void execute(JobExecutionContext context)
				throws JobExecutionException {
			TaQuartzScheduler qs = (TaQuartzScheduler) context
					.get("TaQuartzScheduler.this");
			qs.execute(context);
		}

	}

	private SchedulerFactory schedulerFactory;

	private Scheduler scheduler;

	private Map<String, List<TaSchdulerListener>> listenerListMap = new HashMap<String, List<TaSchdulerListener>>();

	public TaQuartzScheduler() {
		schedulerFactory = new StdSchedulerFactory();
		try {
			scheduler = schedulerFactory.getScheduler();
		} catch (SchedulerException e) {
			throw new TaException(e);
		}
	}
	public void execute(JobExecutionContext context) {
		String triggerId = (String) context.get("triggerId");
		List<TaSchdulerListener> ll = this.listenerListMap.get(triggerId);
		if (ll == null) {
			return;
		}
		for (TaSchdulerListener l : ll) {
			l.onTriggered(triggerId,
					(Map<String, Object>) context.get("mapData"));
		}
	}

	@Override
	public void start() {
		try {
			this.scheduler.start();
		} catch (SchedulerException e) {
			throw new TaException(e);
		}
	}
	
	@Override
	public void stop() {
		try {
			this.scheduler.shutdown();
		} catch (SchedulerException e) {
			throw new TaException(e);
		}
	}

	@Override
	public void addListener(String trigger, TaSchdulerListener listener) {
		List<TaSchdulerListener> list = this.listenerListMap.get(trigger);
		if (list == null) {
			list = new ArrayList<TaSchdulerListener>();
			this.listenerListMap.put(trigger, list);
		}
		list.add(listener);
	}

	@Override
	public void schedule(String triggerId, Date start, TaTimeSpan interval,
			Map<String, Object> data) {

		SimpleTriggerImpl trigger = new SimpleTriggerImpl();
		trigger.setStartTime(start);//
		trigger.setRepeatInterval(interval.getAsMiliSecond());
		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put("TaQuartzScheduler.this", this);
		jobDataMap.put("triggerId", triggerId);//
		jobDataMap.put("mapData", data);//
		trigger.setJobDataMap(jobDataMap);
		JobKey jobK = new JobKey(triggerId + ".jobKey");
		trigger.setJobKey(jobK);
		JobDetailImpl jobDetail = new JobDetailImpl();
		jobDetail.setKey(jobK);//
		jobDetail.setJobClass(TheJob.class);
		try {
			this.scheduler.addJob(jobDetail, true);
			this.scheduler.scheduleJob(trigger);
		} catch (SchedulerException e) {
			throw new TaException(e);
		}
	}

	@Override
	public void addQueue(String trigger,
			BlockingQueue<Map<String, Object>> queue) {
		this.addListener(trigger, new TaQueueSchedulerListener(queue));

	}

}
