/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 21, 2012
 */
package com.graphscape.gwt.core.provider.default_.scheduler;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.user.client.Timer;
import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.HandlerI;
import com.graphscape.gwt.core.UiException;
import com.graphscape.gwt.core.commons.Path;
import com.graphscape.gwt.core.core.Event.EventHandlerI;
import com.graphscape.gwt.core.event.ScheduleEvent;
import com.graphscape.gwt.core.logger.UiLoggerFactory;
import com.graphscape.gwt.core.logger.UiLoggerI;
import com.graphscape.gwt.core.scheduler.SchedulerI;
import com.graphscape.gwt.core.support.UiObjectSupport;

/**
 * @author wu
 * 
 */
public class SchedulerImpl extends UiObjectSupport implements SchedulerI, ContainerI.AwareI {

	private static final UiLoggerI LOG = UiLoggerFactory.getLogger(SchedulerImpl.class);

	/**
	 * @param c
	 */
	public SchedulerImpl(ContainerI c) {
		super(c);
	}

	private static class ScheduleTask implements RepeatingCommand {

		public SchedulerImpl scheduler;

		public String name;

		public int intervalMS;

		public Path path;

		public ScheduleTask(SchedulerImpl sch, String name, int ims) {
			this.scheduler = sch;
			this.name = name;
			this.intervalMS = ims;
			this.path = ScheduleEvent.TYPE.getAsPath().getSubPath(this.name);
		}

		/*
		 * Jan 11, 2013
		 */
		@Override
		public boolean execute() {
			//
			//LOG.debug("execute task:" + this.name);
			new ScheduleEvent(this.scheduler, path).dispatch();
			return true;
		}

	}

	private ContainerI container;

	private Map<String, ScheduleTask> taskMap = new HashMap<String, ScheduleTask>();

	@Override
	public void scheduleRepeat(String name, int intervalMS) {
		LOG.info("scheduleRepeat,name:" + name + ",intervalMS:" + intervalMS);
		ScheduleTask rt = this.getTask(name, false);
		if (rt == null) {
			ScheduleTask rti = new ScheduleTask(this, name, intervalMS);
			Scheduler.get().scheduleFixedPeriod(rti, rti.intervalMS);
		} else {
			throw new UiException("schedule task already exist:" + rt);
		}
	}

	/*
	 * Oct 21, 2012
	 */
	@Override
	public void scheduleRepeat(String name, int intervalMS, EventHandlerI<ScheduleEvent> eh) {
		this.scheduleRepeat(name, intervalMS);
		Path p = ScheduleEvent.TYPE.getAsPath().getSubPath(name);
		this.addHandler(p, eh);

	}

	public ScheduleTask getTask(String name, boolean force) {
		ScheduleTask rt = this.taskMap.get(name);

		if (rt == null && force) {
			throw new UiException("no task by name:" + name);
		}
		return rt;
	}

	/*
	 * Oct 21, 2012
	 */
	@Override
	public void init(ContainerI c) {
		//
		this.container = c;
	}

	@Override
	public ContainerI getContainer() {
		return this.container;
	}

	/*
	 * Jan 11, 2013
	 */
	@Override
	public void cancel(String name) {
		throw new UiException("TODO");
	}

	/*
	 * Mar 30, 2013
	 */
	@Override
	public void scheduleTimer(int delay, final HandlerI<Object> eh) {
		//
		new Timer() {

			@Override
			public void run() {
				//
				eh.handle(null);
			}
		}.schedule(delay);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.commons.uicore.api.gwt.client.scheduler.SchedulerI#scheduleDelay(com.fs
	 * .uicore.api.gwt.client.HandlerI)
	 */
	@Override
	public void scheduleDelay(final HandlerI<Object> eh) {
		new Timer() {

			@Override
			public void run() {
				//
				eh.handle(null);
			}
		}.schedule(0);

	}
}
