package org.ta.test.indicator;

import java.io.IOException;

import org.ta.trader.hista.indicator.TaAverageTrueRangeIndicator;
import org.ta.trader.hista.indicator.TaIndicator;
import org.ta.trader.hista.tohlcv.TaTohlcvWindow;

public class TaAverageTrueRangeIndicatorTest extends TaIndicatorTestSupport {
	int days = 14;

	public void testMACD12_26_19971201() throws IOException {

		String[] timesExp = new String[] { "2014.01.01 00.00.00",//
				"2014.02.01 00.00.00",//
				"2014.03.01 00.00.00",//
				"2014.04.01 00.00.00",//
				"2014.05.01 00.00.00",//
		};
		double[] ma12Exp = new double[] {//
		0.04404, 0.04344, 0.04104, 0.03776, 0.03792 //
		};

		this.doTestIndicator("EURUSD43200-19971201-20150415.csv",
				"2013.12.01 00.00.00", timesExp, ma12Exp, 5);

	}

	protected TaIndicator newIndicator(TaTohlcvWindow window) {
		return new TaAverageTrueRangeIndicator(days, window);
	}

}
