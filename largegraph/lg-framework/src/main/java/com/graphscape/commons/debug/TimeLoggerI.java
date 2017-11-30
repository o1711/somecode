/**
 * Jan 19, 2014
 */
package com.graphscape.commons.debug;

import com.graphscape.commons.util.TimeAndUnit;

/**
 * @author wuzhen0808@gmail.com
 *
 */
public interface TimeLoggerI {
	
	public TimeAndUnit getTotal();
	
	public TimeAndUnit getAvg();
	
	public long getAvgMicros();
	
	public void reset();
	
	public void beforeExecute();
	
	public void afterExecute();
	
	public long getHits();
	
	public void addHits(long hits);
	
	public void dump();
	
	
}
