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
public class DefaultDebugger implements DebuggerI {

	private static final Logger LOG = LoggerFactory.getLogger(DefaultDebugger.class);

	@Override
	public void enter(TracableI t) {
		if (LOG.isDebugEnabled()) {

			LOG.debug("enter tracable:" + t.getRootId() + "\t" + t.getParentId() + "\t" + t.getId()
					+ "\tdetail:" + t.toString());
		}

	}

	@Override
	public void exit(TracableI t) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("exit  tracable:" + t.getRootId() + "\t" + t.getParentId() + "\t" + t.getId() + "\td");
		}
	}

	@Override
	public void tracingRun(TracableI t, Runnable run) {

	}

	@Override
	public <T> T tracingCall(TracableI t, Callable<T> call) {
		return null;
	}

}
