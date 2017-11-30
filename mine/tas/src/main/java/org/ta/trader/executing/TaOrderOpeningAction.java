package org.ta.trader.executing;

import org.ta.trader.TaSide;
import org.ta.trader.hista.syriod.TaSyriod;

public class TaOrderOpeningAction {

	protected boolean openOrder;

	protected TaSyriod syriod;

	protected TaInternalOrder internalOrder;

	public TaOrderOpeningAction(TaSyriod si, TaSide dir) {
		this.syriod = si;
		this.internalOrder = new TaInternalOrder(si.getSymbol(), dir);
	}

	public boolean isOpenOrder() {
		return openOrder;
	}

	public void setOpenOrder(boolean sendOrder) {
		this.openOrder = sendOrder;
	}

	public TaInternalOrder getInternalOrder() {
		return this.internalOrder;
	}

}
