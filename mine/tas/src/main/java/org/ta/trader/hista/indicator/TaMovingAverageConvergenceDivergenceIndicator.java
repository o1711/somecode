package org.ta.trader.hista.indicator;

import org.ta.trader.hista.tohlcv.TaTohlcvWindow;

public class TaMovingAverageConvergenceDivergenceIndicator extends
		TaBaseIndicator {

	TaExponentialMovingAverageIndicator fast;
	TaExponentialMovingAverageIndicator slow;

	public TaMovingAverageConvergenceDivergenceIndicator(int fastDays,
			int slowDays, TaTohlcvWindow window) {
		super(window);
		this.fast = new TaExponentialMovingAverageIndicator(fastDays, window);
		this.slow = new TaExponentialMovingAverageIndicator(slowDays, window);
	}

	@Override
	protected double getValueInternal(int idx) {
		double v1 = this.fast.getValue(idx);
		double v2 = this.slow.getValue(idx);
		return v1 - v2;
	}

}
