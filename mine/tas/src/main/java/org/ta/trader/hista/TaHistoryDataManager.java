package org.ta.trader.hista;

import org.ta.trader.TaSymbol;
import org.ta.trader.hista.syriod.TaSyriod;
import org.ta.trader.hista.syriod.TaSyriodWindow;

public interface TaHistoryDataManager {
	
	public TaSyriodWindow getM1Window(TaSymbol symbol,boolean force);
	
	public TaSyriodWindow getWindow(TaSyriod sd);
	
	public TaSyriodWindow getWindow(TaSyriod sd,boolean force);
	
}
