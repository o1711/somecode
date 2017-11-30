/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 21, 2012
 */
package com.graphscape.gwt.core.scheduler;

import com.graphscape.gwt.core.HandlerI;
import com.graphscape.gwt.core.core.UiObjectI;
import com.graphscape.gwt.core.core.Event.EventHandlerI;
import com.graphscape.gwt.core.event.ScheduleEvent;

/**
 * @author wu
 * 
 */
public interface SchedulerI extends UiObjectI {
	public void scheduleRepeat(String name, int intervelMS);
			
	public void scheduleRepeat(String name, int intervelMS, EventHandlerI<ScheduleEvent> eh);
	
	public void scheduleTimer(int timeout, HandlerI<Object> eh);
	
	public void scheduleDelay(HandlerI<Object> eh);

	public void cancel(String name);
}
