package org.ta.test.director;

import org.ta.director.hmm.TaHmmPlain3SampleCursor;
import org.ta.director.hmm.TaHmmSampleCursor;
import org.ta.trader.hista.tohlcv.TaTohlcvCursor;

public class TaHmmPlain3TrainnerTest extends TaHmmTestSupport {

	public TaHmmPlain3TrainnerTest() {
		super("EURUSD1-20150115-20150331.csv", "EURUSD5-20150401-.csv");
	}
	public void testHmmTra(){
		this.startTest();
	}
	
	@Override
	protected TaHmmSampleCursor newCursor(TaTohlcvCursor cursor) {
		
		return new TaHmmPlain3SampleCursor(10, cursor);
	}
}
