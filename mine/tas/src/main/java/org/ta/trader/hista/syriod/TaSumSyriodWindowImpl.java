package org.ta.trader.hista.syriod;

import org.ta.trader.TaSymbol;
import org.ta.trader.hista.TaPeriod;
import org.ta.trader.hista.tohlcv.TaTohlcv;
import org.ta.trader.hista.tohlcv.TaTohlcvWindow;
import org.ta.trader.hista.tohlcv.TaTohlcvWindowListener;

public class TaSumSyriodWindowImpl extends TaSyriodWindowImpl implements TaTohlcvWindowListener {

	private TaSyriodWindow previous;

	private long periodAsMiliSecond;

	public TaSumSyriodWindowImpl(TaSymbol si, TaPeriod p, TaSyriodWindow previous) {
		super(si, p);
		this.previous = previous;
		this.previous.addListener(this);
		this.periodAsMiliSecond = this.syriod.getPeriod().getAsMiliSecond();
	}

	@Override
	public void onUpdate(TaTohlcvWindow window, int idx, TaTohlcv toh) {
		long time = toh.getTime();
		long timeFirst = (time / this.periodAsMiliSecond) * this.periodAsMiliSecond;
		// do update this values.

		double close = toh.getClose();// close is the last one.
		double high = toh.getHigh();
		double low = toh.getLow();
		double open = toh.getOpen();
		double volume = toh.getVolume();
		for (int i = idx + 1;; i++) {
			TaTohlcv tohI = window.get(i);

			if (tohI == null || tohI.getTime() < timeFirst) {
				break;
			}
			open = tohI.getOpen();// open is the most early one

			if (high < tohI.getHigh()) {
				high = tohI.getHigh();
			}
			if (low > tohI.getLow()) {
				low = tohI.getLow();
			}
			volume += tohI.getVolume();

		}
		this.update(timeFirst, open, high, low, close, volume);
	}

}
