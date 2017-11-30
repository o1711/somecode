package org.ta.test.director;

import java.io.File;

import junit.framework.TestCase;

import org.ta.director.classifier.TaC1Classifier;
import org.ta.director.classifier.TaClassifiedFilterTohlcvCursor;
import org.ta.director.hmm.TaHmmPlain3SampleCursor;
import org.ta.test.data.TaTestData;
import org.ta.trader.hista.tohlcv.TaFileTimeBarsCursor;
import org.ta.trader.hista.tohlcv.TaTohlcvCursor;
import org.ta.trader.hista.tohlcv.TaTohlcvWindow;
import org.ta.trader.hista.tohlcv.TaTohlcvWindowImpl;

public class TaC1ClassifierFilterTest extends TestCase {
	private String file = "EURUSD5-20150401-.csv";

	public void test() {

		TaTohlcvCursor cursor = TaFileTimeBarsCursor.valueOf(new File(TaTestData.dataHomeFile(), this.file));

		TaTohlcvWindow window = new TaTohlcvWindowImpl(100);//
		TaC1Classifier cl = new TaC1Classifier(TaHmmPlain3SampleCursor.DENSITY,window);
		TaClassifiedFilterTohlcvCursor cursor2 = new TaClassifiedFilterTohlcvCursor(window,cursor, cl, 410);

		while(cursor2.next()){
			cursor2.get().println(System.out);			
		}
	}
	/**
	 * <code></code>
	 **/
}
