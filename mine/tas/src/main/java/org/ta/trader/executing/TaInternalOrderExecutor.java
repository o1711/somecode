package org.ta.trader.executing;

import java.util.List;

import org.ta.common.data.TaObjectData;

public interface TaInternalOrderExecutor extends TaOrderExecutor {

	public void open(TaInternalOrder io);

	public boolean close(TaOrderCloseCommand occ);

	public boolean modify(TaOrderModifyCommand omc);

	public void flush(TaObjectData data);

	public void update(List<TaOrderInfo> oiL);

	public List<String> getOrderMagicList();

	public List<String> getOrderIdList();

	public TaInternalOrder getOrderById(String id);

	public TaInternalOrder getOrderByMagic(String magic);

}
