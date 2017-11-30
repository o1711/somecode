package org.ta.trader.hista.indicator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ta.trader.hista.tohlcv.TaTohlcvWindow;

public class TaExponentialMovingAverageIndicator extends TaBaseIndicator
		implements TaIndicator {

	private static final Logger LOG = LoggerFactory
			.getLogger(TaExponentialMovingAverageIndicator.class);

	private int days;

	private double alpha;

	public TaExponentialMovingAverageIndicator(int days, TaTohlcvWindow window) {
		super(window);
		this.days = days;
		this.alpha = 2.0 / (this.days + 1);//

	}

	@Override
	protected double getValueInternal(int idx) {

		if (idx == this.window.size() - 1) {
			return this.window.getClose(idx);
		}

		double p = this.window.getClose(idx);
		double ema0 = this.getValueInternal(idx + 1);
		return this.alpha * p + (1 - this.alpha) * ema0;
	}

}
