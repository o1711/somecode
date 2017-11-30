package org.ta.test.indicator;

import java.io.IOException;

import junit.framework.Assert;

import org.ta.common.config.TaException;
import org.ta.common.util.TaMathUtil;
import org.ta.common.util.TaTimeUtil;
import org.ta.test.TaTestSupport;
import org.ta.test.data.TaTestData;
import org.ta.trader.hista.indicator.TaIndicator;
import org.ta.trader.hista.tohlcv.TaTimeBarsFile;
import org.ta.trader.hista.tohlcv.TaTohlcvCursor;
import org.ta.trader.hista.tohlcv.TaTohlcvWindow;
import org.ta.trader.hista.tohlcv.TaTohlcvWindowImpl;

public abstract class TaIndicatorTestSupport extends TaTestSupport {

	protected abstract TaIndicator newIndicator(TaTohlcvWindow window);

	public void doTestIndicator(String dataFile, String fromTime,
			String[] timesExp, double[] ma12Exp, int digits) throws IOException {

		TaTimeBarsFile file = this.loadTohlcvFile(
				TaTestData.class.getPackage(), dataFile);

		TaTohlcvWindowImpl window = new TaTohlcvWindowImpl();
		TaTohlcvCursor cursor = file.cursor();

		long time0 = TaTimeUtil.parse(fromTime);

		window.updateAll(cursor, 0, time0);
		if (window.size() < 100) {
			throw new TaException("no enough data:" + window.size());
		}
		TaIndicator mai = this.newIndicator(window);

		String[] times = new String[timesExp.length];

		cursor.previous();
		double[] ma12 = new double[times.length];

		for (int i = 0; i < ma12.length; i++) {
			cursor.next();
			window.update(cursor.get());

			times[i] = TaTimeUtil.format(window.getTime());
			ma12[i] = mai.getValue(0);

		}
		TaMathUtil.roundAll(ma12, digits);//

		for (int i = 0; i < ma12.length; i++) {
			Assert.assertEquals("", timesExp[i], times[i]);//
			Assert.assertEquals("atr14 of " + times[i], ma12Exp[i], ma12[i]);
		}

	}

}
