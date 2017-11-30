/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 8, 2012
 */
package com.graphscape.gwt.core.logger;

import java.util.Date;

import com.graphscape.gwt.core.Console;
import com.graphscape.gwt.core.support.UiLoggerSupport;
import com.graphscape.gwt.core.util.ExceptionUtil;

/**
 * @author wu
 * 
 */
public class SimpleUiLogger extends UiLoggerSupport {

	public SimpleUiLogger(String name) {
		super(name);
	}

	@Override
	public void doLog(int level, Object msg, Throwable t) {
		String levelS = levelName.get(level);
		String log = "[" + new Date() + "][" + levelS + "][" + this.name + "]-" + msg;
		Console.getInstance().println(log);

		if (t != null) {
			String more = ExceptionUtil.getStacktraceAsString(t, "\n");
			Console.getInstance().println("throwable:" + more);//

		}

	}

}
