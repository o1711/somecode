package org.ta.trader.manage;

import org.ta.common.data.TaObjectData;
import org.ta.common.lifecycle.TaLifeCycle;

public interface TaManageServer extends TaLifeCycle{
	
	public TaObjectData process(TaObjectData data);
	
}
