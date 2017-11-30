/**
 * Dec 12, 2011
 */
package org.ta.common.util;

import org.ta.common.config.TaException;



/**
 * @author wuzhen
 * 
 */
public class TaTimeSpan {

	private String rawValue;

	private long timeMicroSeconds;

	private TaTimeSpan(String rateS, long ms) {
		this.rawValue = rateS;
		this.timeMicroSeconds = ms;
	}

	public static TaTimeSpan valueOf(String rateS) {

		String[] rs = rateS.split("/");
		long ms = 0;
		if (rs.length == 1) {
			ms = parseTimeAsMicros(rateS);
		} else if (rs.length == 2) {// 60/H,10/S
			ms = parseRateAsMicros(rs);
		}

		return new TaTimeSpan(rateS, ms);
	}

	private static long parseTimeAsMicros(String timeS) {
		long unit = 1;

		if (timeS.endsWith("MS")) {//milli second
			unit = 1;
			timeS = timeS.substring(0, timeS.length() - 2);
		} else if (timeS.endsWith("MI")) {// minitues
			unit = 1000 * 60;
			timeS = timeS.substring(0, timeS.length() - 2);
		} else if (timeS.endsWith("S")) {// second
			unit = 1000;
			timeS = timeS.substring(0, timeS.length() - 1);
		} else {
			throw new TaException("format:" + timeS);

		}
		long time = Long.parseLong(timeS);

		return time * unit * 1000;
	}

	private static long parseRateAsMicros(String[] rs) {

		int alarm = 1;

		alarm = Integer.parseInt(rs[0]);
		if (alarm <= 0) {
			throw new TaException("format error:" + rs);
		}

		//
		String r1 = rs[1];

		long timeUnit = 0;
		long time = 1;

		if (r1.endsWith("S")) {

			timeUnit = 1000;
			if (r1.length() > "S".length()) {
				String tS = r1.substring(0, r1.length() - 2);
				time = Integer.parseInt(tS);
			}

		} else if (r1.equals("H")) {

			timeUnit = 1000L * 3600L;
			if (r1.length() > "H".length()) {
				String tS = r1.substring(0, r1.length() - 2);
				time = Integer.parseInt(tS);
			}
		} else {
			throw new TaException("format error:" + rs);
		}

		long rt = (time * timeUnit * 1000L) / alarm;
		return rt;
	}

	/**
	 * @return the intervalMs
	 */
	public long getAsMiliSecond() {
		return timeMicroSeconds / 1000;
	}

	/**
	 * @return the intervalMs
	 */
	public long getAsMicroSecond() {
		return timeMicroSeconds;
	}

	public long getAsRatePerSecond() {
		return 1000 / this.timeMicroSeconds;
	}

	/**
	 * @return the rawValue
	 */
	public String getRawValue() {
		return rawValue;
	}

	/**
	 * @return
	 */
	public String getAsText() {
		return this.rawValue;
	}

	/**
	 * @param rate
	 * @return
	 */
	public TaTimeSpan multiple(int rate) {
		String rateS = (this.timeMicroSeconds * rate) / 1000 + "MS";//
		TaTimeSpan rt = TaTimeSpan.valueOf(rateS);
		return rt;
	}

}
