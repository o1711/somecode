package org.ta.test.director;

import org.ta.director.hmm.TaHmmPlain2SampleCursor;
import org.ta.director.hmm.TaHmmSampleCursor;
import org.ta.trader.hista.tohlcv.TaTohlcvCursor;

public class TaHmmPlain2TrainnerTest extends TaHmmTestSupport {

	public TaHmmPlain2TrainnerTest() {
		super("EURUSD1-20150115-20150331.csv", "EURUSD5-20150401-.csv");
	}
	public void testHmmTra(){
		this.startTest();
	}
	
	@Override
	protected TaHmmSampleCursor newCursor(TaTohlcvCursor cursor) {
		
		return new TaHmmPlain2SampleCursor(10, cursor);
	}
}
