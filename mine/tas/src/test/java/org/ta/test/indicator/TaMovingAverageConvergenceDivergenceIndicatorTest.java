package org.ta.test.indicator;

import java.io.IOException;

import junit.framework.Assert;

import org.ta.common.config.TaException;
import org.ta.common.util.TaMathUtil;
import org.ta.common.util.TaTimeUtil;
import org.ta.test.TaTestSupport;
import org.ta.test.data.TaTestData;
import org.ta.trader.hista.indicator.TaMovingAverageConvergenceDivergenceIndicator;
import org.ta.trader.hista.syriod.TaSyriod;
import org.ta.trader.hista.tohlcv.TaTimeBarsFile;
import org.ta.trader.hista.tohlcv.TaTohlcvCursor;
import org.ta.trader.hista.tohlcv.TaTohlcvWindowImpl;

public class TaMovingAverageConvergenceDivergenceIndicatorTest extends
		TaTestSupport {

	public void testMACD12_26_19971201() throws IOException {
		int emaFast = 12;
		int emaSlow = 26;

		TaTimeBarsFile file = this.loadTohlcvFile(
				TaTestData.class.getPackage(),
				"EURUSD43200-19971201-20150415.csv");

		TaTohlcvWindowImpl window = new TaTohlcvWindowImpl();
		TaTohlcvCursor cursor = file.cursor();

		long time0 = TaTimeUtil.parse("2013.12.01 00.00.00");

		window.updateAll(cursor, 0, time0);
		if (window.size() < 100) {
			throw new TaException("no enough data:" + window.size());
		}
		TaMovingAverageConvergenceDivergenceIndicator mai = new TaMovingAverageConvergenceDivergenceIndicator(
				emaFast, emaSlow, window);

		String[] timesExp = new String[] { "2014.01.01 00.00.00",//
				"2014.02.01 00.00.00",//
				"2014.03.01 00.00.00",//
				"2014.04.01 00.00.00",//
				"2014.05.01 00.00.00",//
		};
		String[] times = new String[timesExp.length];
		double[] ma12Exp = new double[] {//
		0.006223, 0.009110, 0.010978, 0.013097, 0.012744 //
		};

		cursor.previous();
		double[] ma12 = new double[times.length];

		for (int i = 0; i < ma12.length; i++) {
			cursor.next();
			window.update(cursor.get());

			times[i] = TaTimeUtil.format(window.getTime());
			ma12[i] = mai.getValue(0);

		}
		TaMathUtil.roundAll(ma12, (int) Math.pow(10, 6));//

		for (int i = 0; i < ma12.length; i++) {
			Assert.assertEquals("", timesExp[i], times[i]);//
			Assert.assertEquals("ma12 of " + times[i], ma12Exp[i], ma12[i]);
		}

	}

}
