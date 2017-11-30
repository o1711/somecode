package org.ta.trader.hista.tohlcv;

import org.ta.trader.TaSide;
import org.ta.trader.hista.indicator.TaSimpleMovingAverageIndicator;

public class TaMovingAverageTimeBar extends TaProxyTohlcvWindow {

	private TaSimpleMovingAverageIndicator mai;

	private int days;

	public TaMovingAverageTimeBar(int days, TaTohlcvWindow target) {
		super(target);
		this.days = days;
		this.mai = new TaSimpleMovingAverageIndicator(this.days, this);
	}

	public double getValue(int idx) {
		return this.mai.getValue(idx);

	}

	public boolean isInSide(TaSide dir, int idx, double value) {
		double maI = this.getValue(idx);
		return dir.isSignEquals(value - maI);
	}

	public boolean isOpenInSide(TaSide dir, int idx) {
		return this.isInSide(dir, idx, this.getOpen(idx));
	}

	public boolean isCloseInSide(TaSide dir, int idx) {
		return this.isInSide(dir, idx, this.getClose(idx));
	}

	public boolean isOCInSide(TaSide dir, int idx1, int idx2) {
		boolean rt = true;

		for (int i = idx1; i <= idx2; i++) {
			double maI = this.getValue(i);
			double openI = this.getOpen(i);
			double closeI = this.getClose(i);
			if (!dir.isSignEquals(openI - maI)
					|| !dir.isSignEquals(closeI - maI)) {
				rt = false;
				break;
			}
		}

		return rt;

	}

	public boolean isHorLInSide(TaSide dir, int idx) {
		double highI = this.getHigh(idx);
		if (this.isInSide(dir, idx, highI)) {
			return true;
		}
		double lowI = this.getLow(idx);
		if (this.isInSide(dir, idx, lowI)) {
			return true;
		}
		return false;
	}

	public boolean isHLInSide(TaSide dir, int idx) {
		double maI = this.getValue(idx);
		double highI = this.getHigh(idx);
		double lowI = this.getLow(idx);
		if (!dir.isSignEquals(highI - maI) || !dir.isSignEquals(lowI - maI)) {
			return false;
		}
		return true;
	}

	public boolean isOCInSide(TaSide dir, int idx) {
		double maI = this.getValue(idx);
		double openI = this.getOpen(idx);
		double closeI = this.getClose(idx);
		if (!dir.isSignEquals(openI - maI) || !dir.isSignEquals(closeI - maI)) {
			return false;
		}
		return true;
	}

	public boolean isHLInSide(TaSide dir, int idx1, int idx2) {
		boolean rt = true;

		for (int i = idx1; i <= idx2; i++) {
			double maI = this.getValue(i);
			double highI = this.getHigh(i);
			double lowI = this.getLow(i);
			if (!dir.isSignEquals(highI - maI) || !dir.isSignEquals(lowI - maI)) {
				rt = false;
				break;
			}
		}

		return rt;

	}
}
