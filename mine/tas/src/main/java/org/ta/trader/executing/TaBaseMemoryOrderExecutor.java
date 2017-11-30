package org.ta.trader.executing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class TaBaseMemoryOrderExecutor implements TaExternalOrderExecutor {

	protected Map<String, TaOrderInfo> orderInfoMap = new HashMap<String, TaOrderInfo>();

	protected TaOrderListeners listeners = new TaOrderListeners();

	@Override
	public List<String> getOrderIdList() {
		List<String> rt = new ArrayList<String>();
		rt.addAll(this.orderInfoMap.keySet());
		return rt;
	}

	@Override
	public TaOrderInfo getOrderInfo(String orderId) {
		return this.orderInfoMap.get(orderId);

	}

	@Override
	public void addListener(TaOrderListener lis) {
		this.listeners.addListener(lis);
	}

	@Override
	public List<TaOrderInfo> getOrderInfoList() {
		List<TaOrderInfo> rt = new ArrayList<TaOrderInfo>();
		rt.addAll(this.orderInfoMap.values());
		return rt;
	}
}
