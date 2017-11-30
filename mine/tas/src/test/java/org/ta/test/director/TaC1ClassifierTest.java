package org.ta.test.director;

import java.util.Random;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.ta.common.util.TaTimeUtil;
import org.ta.director.classifier.TaC1Classifier;
import org.ta.trader.hista.tohlcv.TaTohlcvWindow;
import org.ta.trader.hista.tohlcv.TaTohlcvWindowImpl;

public class TaC1ClassifierTest extends TestCase {
	
	public void test() {
		
		TaTohlcvWindow w = new TaTohlcvWindowImpl();
		TaC1Classifier cl = new TaC1Classifier(2,w);
		int total = cl.getTotalClassifications();

		
		
		Random r = new Random();
		long time = TaTimeUtil.parse("2000.01.01 00.00.00");
		for (int i = 0; i < 1000; i++) {

			double open = r.nextDouble();
			double high = open;
			double low = open;
			double close = open;

			for (int j = 0; j < 10; j++) {
				close = r.nextDouble();
				high = (close > high ? close : high);
				low = (close < low ? close : low);
			}
			
			w.update(time, open, high, low, close, 0);//
			
			time = time + 5*60*1000;
			
			int clsI = cl.classify(0);

			System.out.println("cls:" + clsI + "," + open + "," + high + ","
					+ low + "," + close);

			Assert.assertTrue("cls:" + clsI + ">  total:" + total, clsI < total);//
		}

	}

}
