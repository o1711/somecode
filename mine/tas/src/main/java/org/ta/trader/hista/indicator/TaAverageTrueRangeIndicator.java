package org.ta.trader.hista.indicator;

import org.ta.trader.hista.tohlcv.TaTohlcvWindow;

public class TaAverageTrueRangeIndicator extends TaBaseIndicator {

	private int days;

	public TaAverageTrueRangeIndicator(int days, TaTohlcvWindow window) {
		super(window);
		this.days = days;
	}

	@Override
	protected double getValueInternal(int idx) {

		int idxLeft = idx + days;

		if (idxLeft > this.window.size() - 1) {
			return 0;// TODO null.
		} else {
			double total = 0;
			for (int i = idxLeft - 1; i >= idx; i--) {
				double tr = tr(i);
				total += tr;
			}
			return total / this.days;
		}
	}

	protected double tr(int idx) {
		double close0 = this.window.getClose(idx + 1);
		double high = this.window.getHigh(idx);
		double low = this.window.getLow(idx);

		return Math.max(high - low,
				Math.max(Math.abs(high - close0), Math.abs(low - close0)));
	}

}
