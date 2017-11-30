package org.ta.trader.speculate.ma2;

import org.ta.trader.hista.syriod.TaSyriodWindow;
import org.ta.trader.hista.tohlcv.TaTwoMovingAverageTimeBar;
import org.ta.trader.speculate.TaStage2OrderUpdator;

public class TaMa2OrderUpdator extends TaStage2OrderUpdator {
	private TaTwoMovingAverageTimeBar twoMa;

	public TaMa2OrderUpdator(TaSyriodWindow window) {
		super(window);
		this.twoMa = new TaTwoMovingAverageTimeBar(20, 100, window);
	}

	@Override
	protected double calculateFirstProfitForStage2(double stoploss2) {

		double rt = 0.00100;// TODO TaLang::__iATR(NULL,TaLang::PERIOD_D1_,10,0);
		rt = rt * 5.0;
		return rt;
	}

	@Override
	protected double calculateStopLossForStage2() {

		return this.twoMa.getMidValue(0);

	}
}
