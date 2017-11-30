package org.ta.trader.speculate;

import org.ta.trader.executing.TaOrderOpeningAction;

public interface TaOrderOpenner {

	public boolean tryOpenOrder(TaOrderOpeningAction pA);

}
