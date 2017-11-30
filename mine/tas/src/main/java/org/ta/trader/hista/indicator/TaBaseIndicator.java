package org.ta.trader.hista.indicator;

import org.ta.trader.hista.tohlcv.TaTohlcvWindow;

public abstract class TaBaseIndicator implements TaIndicator {

	protected TaTohlcvWindow window;

	public TaBaseIndicator(TaTohlcvWindow window) {
		this.window = window;
	}

	@Override
	public double getValue(int index) {
		//
		double rt = this.getValueInternal(index);		
		return rt;
	}

	protected abstract double getValueInternal(int idx);

}
