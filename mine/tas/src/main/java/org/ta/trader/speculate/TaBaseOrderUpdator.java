package org.ta.trader.speculate;

import org.ta.trader.TaSide;
import org.ta.trader.TaSymbol;
import org.ta.trader.executing.TaOrderUpdatingAction;
import org.ta.trader.hista.syriod.TaSyriodWindow;
import org.ta.trader.rating.TaRateMonitor;

public abstract class TaBaseOrderUpdator implements TaOrderUpdator {

	private TaSyriodWindow window;

	private TaRateMonitor rateMonitor;

	private TaSymbol symbol;

	public TaBaseOrderUpdator(TaSyriodWindow window) {
		this.window = window;
	}

	protected void closeOrder(TaOrderUpdatingAction uA) {
		double lotsToClose = uA.getInternalOrder().getOrderInfo().getLots();
		this.closeOrder(uA, lotsToClose);
	}

	protected void closeOrder(TaOrderUpdatingAction uA, double lots) {
		double currentPrice = this.getCurrentPrice(uA.getInternalOrder()
				.getDirection());
		uA.closeOrder(currentPrice, lots);
	}

	public double getCurrentPrice(TaSide direction) {
		if (direction.isUp()) {
			return this.rateMonitor.getRate(this.symbol).getBid();
		} else {
			return this.rateMonitor.getRate(this.symbol).getAsk();
		}
	}

}
