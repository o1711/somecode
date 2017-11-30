package org.ta.trader.executing;

import java.util.ArrayList;
import java.util.List;

public class TaOrderListeners implements TaOrderListener {
	private List<TaOrderListener> listenerList = new ArrayList<TaOrderListener>();

	@Override
	public void onOrderOpenned(TaOrderInfo oi) {
		for (TaOrderListener l : this.listenerList) {
			l.onOrderOpenned(oi);//
		}
	}

	public void addListener(TaOrderListener lis) {
		this.listenerList.add(lis);
	}
}
