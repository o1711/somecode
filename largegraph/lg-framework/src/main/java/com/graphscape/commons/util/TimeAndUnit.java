/**
 * Dec 15, 2013
 */
package com.graphscape.commons.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.graphscape.commons.lang.GsException;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class TimeAndUnit {

	private static Map<String, TimeUnit> TIME_UNIT_MAP = new HashMap<String, TimeUnit>();
	private static Map<TimeUnit, String> TIME_UNIT_MAP2 = new HashMap<TimeUnit, String>();
	static {
		TIME_UNIT_MAP.put("D", TimeUnit.DAYS);
		TIME_UNIT_MAP.put("H", TimeUnit.HOURS);
		TIME_UNIT_MAP.put("M", TimeUnit.MINUTES);
		TIME_UNIT_MAP.put("S", TimeUnit.SECONDS);
		TIME_UNIT_MAP.put("mS", TimeUnit.MILLISECONDS);
		TIME_UNIT_MAP.put("uS", TimeUnit.MICROSECONDS);
		TIME_UNIT_MAP.put("nS", TimeUnit.NANOSECONDS);
		TIME_UNIT_MAP2 = CollectionUtil.reverse(TIME_UNIT_MAP);
	}

	@Override
	public int hashCode() {
		return (int) this.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof TimeAndUnit)) {
			return false;
		}

		return this.value == ((TimeAndUnit) obj).value
				&& ObjectUtil.nullSafeEquals(this.unit, ((TimeAndUnit) obj).unit);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		return this.value + "" + TIME_UNIT_MAP2.get(this.unit);
	}

	private long value;

	private TimeUnit unit;

	private TimeAndUnit(long value, TimeUnit tu) {
		this.value = value;
		this.unit = tu;
	}

	public void increase(long value) {
		this.value += value;
	}

	public static TimeAndUnit valueOf(long value, TimeUnit tu) {
		return new TimeAndUnit(value, tu);
	}

	public static TimeAndUnit parse(String timeS) {
		if (timeS.length() < 2) {
			throw new GsException("cannot parsing:" + timeS);
		}

		String unitS = timeS.substring(timeS.length() - 2);
		TimeUnit tu = TIME_UNIT_MAP.get(unitS);

		if (tu == null) {
			unitS = unitS.substring(unitS.length() - 1);
			tu = TIME_UNIT_MAP.get(unitS);
		}
		if (tu == null) {
			throw new GsException("cannot parsing:" + timeS);
		}
		String valueS = timeS.substring(0, timeS.length() - unitS.length());
		int v = Integer.parseInt(valueS);

		return valueOf(v, tu);
	}

	public long getAsMicros() {
		return this.unit.toMicros(this.value);
	}

	public long toMillis() {
		return this.unit.toMillis(this.value);
	}

	/**
	 * @return the value
	 */
	public long getValue() {
		return value;
	}

	/**
	 * @return the unit
	 */
	public TimeUnit getUnit() {
		return unit;
	}

	public void setValue(long value) {
		this.value = value;
	}

	/**
	 * @param i
	 * @return
	 */
	public TimeAndUnit divide(int i) {

		return TimeAndUnit.valueOf(this.value / i, this.unit);

	}

}
