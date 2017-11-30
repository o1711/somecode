/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 21, 2012
 */
package com.graphscape.gwt.core.event;

import com.graphscape.gwt.core.commons.Path;
import com.graphscape.gwt.core.core.Event;
import com.graphscape.gwt.core.scheduler.SchedulerI;

/**
 * @author wu
 * 
 */
public class ScheduleEvent extends Event {

	public static final Type<ScheduleEvent> TYPE = new Type<ScheduleEvent>("schedule");

	/**
	 * @param type
	 */
	public ScheduleEvent(SchedulerI src, Path path) {
		super(TYPE, src, path);
	}

	public String getTask() {
		return this.getPath().getName();
	}

}
