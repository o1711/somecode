package org.ta.test.indicator;

import java.io.IOException;

import junit.framework.Assert;

import org.ta.common.config.TaException;
import org.ta.common.util.TaMathUtil;
import org.ta.common.util.TaTimeUtil;
import org.ta.test.TaTestSupport;
import org.ta.trader.hista.indicator.TaExponentialMovingAverageIndicator;
import org.ta.trader.hista.syriod.TaSyriod;
import org.ta.trader.hista.tohlcv.TaTimeBarsFile;
import org.ta.trader.hista.tohlcv.TaTohlcvCursor;
import org.ta.trader.hista.tohlcv.TaTohlcvWindowImpl;

public class TaExponentialMovingAverageIndicatorTest extends TaTestSupport {

	public void testMA2_20150413() throws IOException {
		int maDays = 2;
		TaTimeBarsFile file = this.loadTohlcvFile(this.getClass().getPackage(),
				"TaMaIndicatorTest-20150413.csv");

		TaTohlcvWindowImpl window = new TaTohlcvWindowImpl();
		TaTohlcvCursor cursor = file.cursor();

		for (int i = 0; i < maDays - 1; i++) {
			cursor.next();
			window.update(cursor.get());
		}

		TaExponentialMovingAverageIndicator mai = new TaExponentialMovingAverageIndicator(
				maDays, window);

		String[] timesExp = new String[] { "2015.04.13 07.05.00",
				"2015.04.13 07.10.00" };
		String[] times = new String[timesExp.length];
		double[] ema2Exp = new double[] { 1.05952, 1.05921 };

		double[] ema2 = new double[timesExp.length];

		for (int i = 0; i < ema2.length; i++) {
			cursor.next();
			window.update(cursor.get());

			times[i] = TaTimeUtil.format(window.getTime());
			ema2[i] = mai.getValue(0);

		}
		TaMathUtil.roundAll(ema2, 5);//

		for (int i = 0; i < ema2.length; i++) {
			Assert.assertEquals("", timesExp[i], times[i]);//
			Assert.assertEquals("ma12 of " + times[i], ema2Exp[i], ema2[i]);
		}

	}

	public void testMA12_20150413() throws IOException {
		int maDays = 12;

		TaTimeBarsFile file = this.loadTohlcvFile(this.getClass().getPackage(),
				"TaMaIndicatorTest-20150413.csv");

		TaTohlcvWindowImpl window = new TaTohlcvWindowImpl();
		TaTohlcvCursor cursor = file.cursor();
		
		long time0 = TaTimeUtil.parse("2015.04.13 08.55.00");
		
		for (;;) {
			if(!cursor.next()){
				throw new TaException("no enough data.");
			}
			window.update(cursor.get());
			if (window.getTime() == time0) {
				break;
			}
		}

		TaExponentialMovingAverageIndicator mai = new TaExponentialMovingAverageIndicator(
				maDays, window);

		String[] timesExp = new String[] { "2015.04.13 09.00.00",//
				"2015.04.13 09.05.00",//
				"2015.04.13 09.10.00",//
				"2015.04.13 09.15.00",//
				"2015.04.13 09.20.00"//

		};
		String[] times = new String[timesExp.length];
		double[] ma12Exp = new double[] {//
		1.05891, 1.05911, 1.05920, 1.05927, 1.05934,//
		};

		double[] ma12 = new double[times.length];

		for (int i = 0; i < ma12.length; i++) {
			cursor.next();
			window.update(cursor.get());

			times[i] = TaTimeUtil.format(window.getTime());
			ma12[i] = mai.getValue(0);

		}
		TaMathUtil.roundAll(ma12, 5);//

		for (int i = 0; i < ma12.length; i++) {
			Assert.assertEquals("", timesExp[i], times[i]);//
			Assert.assertEquals("ma12 of " + times[i], ma12Exp[i], ma12[i]);
		}

	}

}
