/**
 *  
 */
package com.graphscape.commons.lang;

import java.util.concurrent.Callable;

/**
 * @author wu
 * 
 */
public interface DebuggerI {

	public void enter(TracableI t);

	public void exit(TracableI t);

	public void tracingRun(TracableI t, Runnable run);

	public <T> T tracingCall(TracableI t, Callable<T> call);

}
