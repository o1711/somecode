package org.ta.test.indicator;

import java.io.IOException;

import junit.framework.Assert;

import org.ta.common.util.TaMathUtil;
import org.ta.common.util.TaTimeUtil;
import org.ta.test.TaTestSupport;
import org.ta.trader.hista.indicator.TaSimpleMovingAverageIndicator;
import org.ta.trader.hista.syriod.TaSyriod;
import org.ta.trader.hista.tohlcv.TaTimeBarsFile;
import org.ta.trader.hista.tohlcv.TaTohlcvCursor;
import org.ta.trader.hista.tohlcv.TaTohlcvWindowImpl;

public class TaSimpleMovingAverageIndicatorTest extends TaTestSupport {

	public void testMA20_20150401() throws IOException {
		int maDays = 20;
		TaTimeBarsFile file = this.loadTohlcvFile(this.getClass().getPackage(),
				"TaMaIndicatorTest-tohlcv.csv");

		TaTohlcvWindowImpl window = new TaTohlcvWindowImpl();
		TaTohlcvCursor cursor = file.cursor();

		for (int i = 0; i < maDays - 1; i++) {
			cursor.next();
			window.update(cursor.get());
		}

		TaSimpleMovingAverageIndicator mai = new TaSimpleMovingAverageIndicator(
				20, window);

		String[] timesExp = new String[] { "2015.04.01 01.35.00",
				"2015.04.01 01.40.00",
				"2015.04.01 01.45.00",
				"2015.04.01 01.50.00",
				"2015.04.01 01.55.00",//
				"2015.04.01 02.00.00", "2015.04.01 02.05.00",
				"2015.04.01 02.10.00", "2015.04.01 02.15.00",
				"2015.04.01 02.20.00" };
		String[] times = new String[timesExp.length];
		double[] ma20Exp = new double[] {//
		1.07387, 1.07388, 1.07390, 1.07391, 1.07393,// 1.55
				1.07394, 1.07394, 1.07394, 1.07393, 1.07391,// 2.20
		};

		double[] ma20 = new double[10];

		for (int i = 0; i < ma20.length; i++) {
			cursor.next();
			window.update(cursor.get());

			times[i] = TaTimeUtil.format(window.getTime());
			ma20[i] = mai.getValue(0);

		}

		TaMathUtil.roundAll(ma20, 5);//

		for (int i = 0; i < ma20.length; i++) {
			Assert.assertEquals("", timesExp[i], times[i]);//
			Assert.assertEquals("ma20 of " + times[i], ma20Exp[i], ma20[i]);
		}

	}

	public void testMA20_20150113() throws IOException {
		int maDays = 20;
		TaTimeBarsFile file = this.loadTohlcvFile(this.getClass().getPackage(),
				"TaMaIndicatorTest-20150413.csv");

		TaTohlcvWindowImpl window = new TaTohlcvWindowImpl();
		TaTohlcvCursor cursor = file.cursor();

		for (int i = 0; i < maDays - 1; i++) {
			cursor.next();
			window.update(cursor.get());
		}

		TaSimpleMovingAverageIndicator mai = new TaSimpleMovingAverageIndicator(
				20, window);

		String[] timesExp = new String[] { "2015.04.13 08.35.00",//
				"2015.04.13 08.40.00",//
				"2015.04.13 08.45.00",//
				"2015.04.13 08.50.00",//
				"2015.04.13 08.55.00",//
				"2015.04.13 09.00.00",//
				"2015.04.13 09.05.00",//
				"2015.04.13 09.10.00", //
				"2015.04.13 09.15.00",//
				"2015.04.13 09.20.00" };
		String[] times = new String[timesExp.length];
		double[] ma20Exp = new double[] {//
		1.05905, 1.05900, 1.05894, 1.05892, 1.05892,//
				1.05895, 1.05901, 1.05903, 1.05904, 1.05906 };

		double[] ma20 = new double[10];

		for (int i = 0; i < ma20.length; i++) {
			cursor.next();
			window.update(cursor.get());

			times[i] = TaTimeUtil.format(window.getTime());
			ma20[i] = mai.getValue(0);

		}
		TaMathUtil.roundAll(ma20, 5);//

		for (int i = 0; i < ma20.length; i++) {
			Assert.assertEquals("", timesExp[i], times[i]);//
			Assert.assertEquals("ma20 of " + times[i], ma20Exp[i], ma20[i]);
		}

	}

}
