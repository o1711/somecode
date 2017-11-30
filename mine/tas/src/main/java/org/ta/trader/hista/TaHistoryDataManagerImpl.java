package org.ta.trader.hista;

import java.util.HashMap;
import java.util.Map;

import org.ta.common.config.TaException;
import org.ta.trader.TaSymbol;
import org.ta.trader.hista.syriod.TaSumSyriodWindowImpl;
import org.ta.trader.hista.syriod.TaSyriod;
import org.ta.trader.hista.syriod.TaSyriodWindow;
import org.ta.trader.hista.syriod.TaSyriodWindowImpl;

public class TaHistoryDataManagerImpl implements TaHistoryDataManager {

	private Map<TaSyriod, TaSyriodWindow> windowMap = new HashMap<TaSyriod, TaSyriodWindow>();


	public TaHistoryDataManagerImpl() {
		TaSymbol sy = TaSymbol.valueOf("EURUSD");
		TaPeriod pe = TaPeriod.M1;
		
		TaSyriodWindow swM1 = new TaSyriodWindowImpl(TaSyriod.valueOf(sy, TaPeriod.M1));
		
		windowMap.put(swM1.getSyriod(), swM1);

		TaSyriodWindow swM5 = new TaSumSyriodWindowImpl(sy, TaPeriod.M5, swM1);
		windowMap.put(swM5.getSyriod(), swM5);

		TaSyriodWindow swM15 = new TaSumSyriodWindowImpl(sy, TaPeriod.M15, swM5);
		windowMap.put(swM15.getSyriod(), swM15);

	}

	@Override
	public TaSyriodWindow getWindow(TaSyriod period) {
		return this.windowMap.get(period);
	}

	@Override
	public TaSyriodWindow getWindow(TaSyriod period, boolean force) {
		TaSyriodWindow rt = this.windowMap.get(period);
		if (rt == null && force) {
			throw new TaException("no this window:" + period + ",all:" + this.windowMap.keySet());
		}
		return rt;
	}

	@Override
	public TaSyriodWindow getM1Window(TaSymbol symbol, boolean force) {
		TaSyriod sd = TaSyriod.valueOf(symbol,TaPeriod.M1);
		return this.getWindow(sd,force);//
	}

}
