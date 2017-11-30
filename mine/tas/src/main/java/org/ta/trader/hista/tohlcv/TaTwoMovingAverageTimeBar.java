package org.ta.trader.hista.tohlcv;

import org.ta.trader.TaSide;

public class TaTwoMovingAverageTimeBar extends TaProxyTohlcvWindow {

	private TaMovingAverageTimeBar ma1;

	private TaMovingAverageTimeBar ma2;

	public TaTwoMovingAverageTimeBar(int days1, int days2, TaTohlcvWindow window) {
		super(window);
		this.ma1 = new TaMovingAverageTimeBar(days1, window);
		this.ma2 = new TaMovingAverageTimeBar(days2, window);
	}

	public double getValue1(int idx) {
		return this.ma1.getValue(idx);
	}

	public double getMidValue(int idx) {
		return (this.ma1.getValue(idx) + this.ma2.getValue(idx)) / 2.0;
	}

	public double getValue2(int idx) {
		return this.ma2.getValue(idx);
	}

	public boolean isAllOpenCloseInSideOfSlowMa(TaSide dir, int idx1, int idx2) {
		return this.ma2.isOCInSide(dir, idx1, idx2);
	}

	public boolean isCloseInSideOfAllMa(TaSide dir, int idx) {
		return this.ma1.isCloseInSide(dir, idx)
				&& this.ma2.isCloseInSide(dir, idx);
	}

	public boolean isOpenInSideOfAllMa(TaSide dir, int idx) {
		return this.ma1.isOpenInSide(dir, idx)
				&& this.ma2.isOpenInSide(dir, idx);
	}

	public boolean isHLInSideOfAllMa(TaSide dir, int idx) {

		return this.ma1.isHLInSide(dir, idx) && this.ma2.isHLInSide(dir, idx);
	}

	public boolean isOCInSideOfAllMa(TaSide dir, int idx) {
		return this.ma1.isOCInSide(dir, idx) && this.ma2.isOCInSide(dir, idx);
	}

	public boolean isInSideOfAllMa(TaSide dir, int idx, double value) {
		return this.ma1.isInSide(dir, idx, value)
				&& this.ma2.isInSide(dir, idx, value);
	}

	public boolean isOCInSideOfAllMa(TaSide dir, int idx1, int idx2) {
		return this.ma1.isOCInSide(dir, idx1, idx2)
				&& this.ma2.isOCInSide(dir, idx1, idx2);
	}

	public boolean isHorLInSideOfAllMa(TaSide dir, int idx) {
		double mA1 = this.ma1.getValue(idx);
		double mA2 = this.ma2.getValue(idx);
		double high = this.getHigh(idx);
		double low = this.getLow(idx);

		if (dir.isUp()) {
			double max = Math.max(mA1, mA2);
			return dir.isSignEquals(high - max) || dir.isSignEquals(low - max);
		} else {
			double min = Math.max(mA1, mA2);
			return dir.isSignEquals(high - min) || dir.isSignEquals(low - min);
		}
	}
}
