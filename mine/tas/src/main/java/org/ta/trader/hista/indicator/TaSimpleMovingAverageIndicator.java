package org.ta.trader.hista.indicator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ta.trader.hista.tohlcv.TaTohlcvWindow;

public class TaSimpleMovingAverageIndicator extends TaBaseIndicator implements
		TaIndicator {

	private static final Logger LOG = LoggerFactory
			.getLogger(TaSimpleMovingAverageIndicator.class);


	private int days;

	public TaSimpleMovingAverageIndicator(int days, TaTohlcvWindow window) {
		super(window);
		this.days = days;
	}

	@Override
	protected double getValueInternal(int idx) {
		double rt = 0;
//		if (idx == 1) {
//			long time0 = this.tohlcvWindow.getTime(0);
//			if (time0 == TaTimeUtil.parse("2015.04.13 09.10.00")) {
//				LOG.info("time0:" + time0);//
//			}
//		}
		for (int i = 0; i < days; i++) {
			double closeI = this.window.getClose(idx + i);
			rt += closeI;
		}
		rt = (rt / days);

		return rt;
	}
}
