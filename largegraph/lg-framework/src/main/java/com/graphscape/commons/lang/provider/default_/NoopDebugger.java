/**
 * 
 */
package com.graphscape.commons.lang.provider.default_;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.graphscape.commons.lang.DebuggerI;
import com.graphscape.commons.lang.TracableI;

/**
 * @author wuzhen
 * 
 */
public class NoopDebugger implements DebuggerI {

	private static final Logger LOG = LoggerFactory.getLogger(NoopDebugger.class);

	@Override
	public void enter(TracableI t) {

	}

	@Override
	public void exit(TracableI t) {

	}

	@Override
	public void tracingRun(TracableI t, Runnable run) {

	}

	@Override
	public <T> T tracingCall(TracableI t, Callable<T> call) {
		return null;
	}

}
