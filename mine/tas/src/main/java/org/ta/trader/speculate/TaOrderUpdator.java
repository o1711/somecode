package org.ta.trader.speculate;

import org.ta.trader.executing.TaOrderUpdatingAction;

public interface TaOrderUpdator {
	boolean tryUpdateOrder(TaOrderUpdatingAction uA);
}
