package org.ta.director.vectorizier;

import org.apache.mahout.math.DenseVector;
import org.apache.mahout.math.Vector;
import org.ta.common.config.TaException;
import org.ta.trader.hista.indicator.TaAverageTrueRangeIndicator;
import org.ta.trader.hista.tohlcv.TaTohlcv;
import org.ta.trader.hista.tohlcv.TaTohlcvWindow;

public class TaV2Vectorizier extends TaBaseVectorizier {
	private TaAverageTrueRangeIndicator atr;

	private int atrDays = 14;

	public TaV2Vectorizier(TaTohlcvWindow w) {
		super(4, w);
		if (w.getMaxWidth() < atrDays) {
			throw new TaException("");
		}
		this.atr = new TaAverageTrueRangeIndicator(14, w);
	}

	@Override
	public Vector vectorize(int idx) {
		if (idx + atrDays > this.window.getMaxWidth()) {
			throw new TaException("no enough data.");
		}
		Vector rt = new DenseVector(this.dimension);
		TaTohlcv toh = window.get(idx);//
		int i = 0;
		double open = toh.getOpen();
		double high = toh.getHigh();
		double low = toh.getLow();
		double close = toh.getClose();
		boolean up = close > open;
		double s1 = up ? low : high;
		double s2 = up ? high : low;
		double range = high - low;
		double atr = this.atr.getValue(idx);//

		rt.set(i++, (s1 - open) / range);
		rt.set(i++, (close - open) / range);
		rt.set(i++, (close - s2) / range);
		rt.set(i++, (range) / (atr * 3));
		rt = rt.normalize();

		return rt;
	}

}
