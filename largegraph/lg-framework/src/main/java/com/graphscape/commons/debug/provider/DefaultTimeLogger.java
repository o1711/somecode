/**
 * Jan 19, 2014
 */
package com.graphscape.commons.debug.provider;

import java.util.concurrent.TimeUnit;

import com.graphscape.commons.debug.TimeLoggerI;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.util.TimeAndUnit;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class DefaultTimeLogger implements TimeLoggerI {

	private TimeAndUnit totalExecuteTime = TimeAndUnit.valueOf(0, TimeUnit.MILLISECONDS);

	private long hits;

	private long executeTimeFrom = -1;

	@Override
	public void beforeExecute() {
		if (this.executeTimeFrom != -1) {
			throw new GsException("");
		}

		this.executeTimeFrom = System.currentTimeMillis();
		hits++;

	}

	@Override
	public void afterExecute() {
		if (this.executeTimeFrom == -1) {
			throw new GsException("time logger not started yet");
		}
		long added = System.currentTimeMillis() - this.executeTimeFrom;
		this.totalExecuteTime.increase(added);
		this.executeTimeFrom = -1;
	}

	@Override
	public TimeAndUnit getTotal() {
		return this.totalExecuteTime;

	}

	@Override
	public TimeAndUnit getAvg() {
		return TimeAndUnit.valueOf(this.getAvgMicros(), TimeUnit.MICROSECONDS);
	}

	@Override
	public long getAvgMicros() {
		if (this.hits == 0) {
			return 0;
		}
		return this.totalExecuteTime.getAsMicros() / this.hits;

	}

	@Override
	public void reset() {
		if (this.executeTimeFrom != -1) {
			throw new IllegalStateException("in execution state.");
		}
		this.totalExecuteTime.setValue(0);
		this.hits = 0;
	}

	@Override
	public void addHits(long hits) {
		this.hits += hits;
	}

	@Override
	public long getHits() {
		return this.hits;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("total:" + this.getTotal() + ",hits:" + this.getHits() + ",avg:" + this.getAvg());
		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.debug.TimeLoggerI#dump()
	 */
	@Override
	public void dump() {
		System.out.println(this.toString());//
	}
}
