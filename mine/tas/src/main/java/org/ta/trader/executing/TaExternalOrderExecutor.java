package org.ta.trader.executing;

import java.util.List;

public interface TaExternalOrderExecutor extends TaOrderExecutor{

	public void open(TaOrderOpenCommand ooc);

	public boolean close(TaOrderCloseCommand occ);

	public boolean modify(TaOrderModifyCommand omc);

	public TaOrderInfo getOrderInfo(String orderId);

	public List<String> getOrderIdList();
	
	public List<TaOrderInfo> getOrderInfoList();
	
	public void addListener(TaOrderListener lis);

	public void update(double ask, double bid);
	
}
