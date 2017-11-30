package org.ta.trader.hista;

import org.ta.common.util.TaTimeUtil;

public enum TaPeriod {

	S1(1), S5(5), M1(1 * 60), M5(5 * 60), M15(15 * 60), M30(30 * 60), H1(
			60 * 60), H4(4 * 60 * 60), D1(24 * 60 * 60), W1(7 * 24 * 60 * 60);

	private int seconds;
	private long miliSecond;

	private TaPeriod(int sec) {
		this.seconds = sec;
		this.miliSecond = this.seconds * TaTimeUtil.MINITUE.getAsMiliSecond();
	}

	public int getSeconds() {
		return seconds;
	}

	public long parent(long mili) {
		TaPeriod up = this.parent();
		long unit = up.getAsMiliSecond();
		long rt = mili / unit;
		return rt;
	}

	public TaPeriod parent() {

		TaPeriod[] all = TaPeriod.values();
		if (this.ordinal() >= all.length - 1) {
			return null;
		}
		return all[this.ordinal() + 1];
	}

	public long getAsMiliSecond() {
		return this.miliSecond;
	}

}
