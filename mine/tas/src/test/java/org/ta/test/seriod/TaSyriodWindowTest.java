package org.ta.test.seriod;

import junit.framework.Assert;

import org.ta.common.util.TaTimeUtil;
import org.ta.test.TaTestSupport;
import org.ta.trader.TaSymbol;
import org.ta.trader.hista.TaPeriod;
import org.ta.trader.hista.syriod.TaSumSyriodWindowImpl;
import org.ta.trader.hista.syriod.TaSyriodWindow;
import org.ta.trader.hista.syriod.TaSyriodWindowImpl;

public class TaSyriodWindowTest extends TaTestSupport {

	public void testParent() {
		TaSymbol sb = TaSymbol.valueOf("EURUSD");
		TaSyriodWindow w1 = new TaSyriodWindowImpl(sb, TaPeriod.M1);
		TaSyriodWindow w5 = new TaSumSyriodWindowImpl(sb, TaPeriod.M5, w1);
		long[] times = TaTimeUtil.parseAll(new String[] { "2015.01.01 0.0.0",//
				"2015.01.01 0.1.0",//
				"2015.01.01 0.2.0", //
				"2015.01.01 0.3.0", //
				"2015.01.01 0.4.0", //
				"2015.01.01 0.5.0" });
		double[] open = new double[] { 1, 2, 3, 4, 5, 6 };
		double[] high = new double[] { 3, 4, 5, 6, 7, 8 };
		double[] low = new double[] { 1, 2, 3, 4, 5, 6 };
		double[] close = new double[] { 2, 3, 4, 5, 6, 7 };
		double[] volume = new double[] { 1, 2, 1, 2, 1, 2 };
		int i = 0;
		{
			w1.update(times[i], open[i], high[i], low[i], close[i], volume[i]);
			Assert.assertNotNull(w1.get(0));
			Assert.assertNull(w1.get(1));

			//
			Assert.assertNotNull(w5.get(0));
			Assert.assertNull(w5.get(1));
		}
		{
			i++;
			w1.update(times[i], open[i], high[i], low[i], close[i], volume[i]);
			Assert.assertNotNull(w1.get(0));
			Assert.assertNotNull(w1.get(1));
			Assert.assertNull(w1.get(2));
			//
			Assert.assertNotNull(w5.get(0));
			Assert.assertNull(w5.get(1));
		}
		{

			i++;
			w1.update(times[i], open[i], high[i], low[i], close[i], volume[i]);
			Assert.assertNotNull(w1.get(0));
			Assert.assertNotNull(w1.get(1));
			Assert.assertNotNull(w1.get(2));
			Assert.assertNull(w1.get(3));
			//
			Assert.assertNotNull(w5.get(0));
			Assert.assertNull(w5.get(1));
		}
		{

			i++;
			w1.update(times[i], open[i], high[i], low[i], close[i], volume[i]);
			Assert.assertNotNull(w1.get(0));
			Assert.assertNotNull(w1.get(1));
			Assert.assertNotNull(w1.get(2));
			Assert.assertNotNull(w1.get(3));
			Assert.assertNull(w1.get(4));
			//

			Assert.assertNotNull(w5.get(0));
			Assert.assertNull(w5.get(1));
		}
		{

			i++;
			w1.update(times[i], open[i], high[i], low[i], close[i], volume[i]);
			Assert.assertNotNull(w1.get(0));
			Assert.assertNotNull(w1.get(1));
			Assert.assertNotNull(w1.get(2));
			Assert.assertNotNull(w1.get(3));
			Assert.assertNotNull(w1.get(4));
			Assert.assertNull(w1.get(5));
			//
			Assert.assertNotNull(w5.get(0));
			Assert.assertNull(w5.get(1));

		}
		{

			i++;
			w1.update(times[i], open[i], high[i], low[i], close[i], volume[i]);
			Assert.assertNotNull(w1.get(0));
			Assert.assertNotNull(w1.get(1));
			Assert.assertNotNull(w1.get(2));
			Assert.assertNotNull(w1.get(3));
			Assert.assertNotNull(w1.get(4));
			Assert.assertNotNull(w1.get(5));
			Assert.assertNull(w1.get(6));
			//
			Assert.assertNotNull(w5.get(0));
			Assert.assertNotNull(w5.get(1));
			Assert.assertNull(w5.get(2));

		}

	}
}
