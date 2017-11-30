/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 6, 2012
 */
package com.graphscape.gwt.core.event;

import com.graphscape.gwt.core.core.Event;
import com.graphscape.gwt.core.core.UiObjectI;
import com.graphscape.gwt.core.logger.UiLoggerI;

/**
 * @author wu
 * 
 */
public class LogEvent extends Event {

	public static final Event.Type<LogEvent> TYPE = new Event.Type<LogEvent>("log");

	private String message;

	private UiLoggerI logger;

	/**
	 * @param type
	 */
	public LogEvent(UiLoggerI logger, String msg, UiObjectI src) {
		super(TYPE, src);
		this.logger = logger;
		this.message = msg;
	}

	/**
	 * @return the message
	 */
	public String getLogMessage() {
		return message;
	}

}