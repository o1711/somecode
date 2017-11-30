package org.ta.trader.hista.syriod;

import org.ta.trader.TaSymbol;
import org.ta.trader.hista.TaPeriod;
import org.ta.trader.hista.tohlcv.TaTohlcvWindowImpl;

public class TaSyriodWindowImpl extends TaTohlcvWindowImpl implements
		TaSyriodWindow {

	protected TaSyriod syriod;

	public TaSyriodWindowImpl(TaSymbol sb, TaPeriod pd) {
		this(TaSyriod.valueOf(sb, pd));//
	}

	public TaSyriodWindowImpl(TaSyriod syriod) {
		this.syriod = syriod;
	}

	@Override
	public TaSyriod getSyriod() {
		return syriod;
	}

}
