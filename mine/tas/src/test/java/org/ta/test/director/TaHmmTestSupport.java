package org.ta.test.director;

import java.io.File;

import junit.framework.TestCase;

import org.apache.mahout.classifier.sequencelearning.hmm.HmmModel;
import org.ta.director.hmm.TaHmmEvaluator;
import org.ta.director.hmm.TaHmmSampleCursor;
import org.ta.director.hmm.TaHmmTrainner;
import org.ta.test.data.TaTestData;
import org.ta.trader.hista.tohlcv.TaFileTimeBarsCursor;
import org.ta.trader.hista.tohlcv.TaTohlcvCursor;

public abstract class TaHmmTestSupport extends TestCase {
	String trainFile;
	String testFile;
	private boolean trace = false;

	public TaHmmTestSupport(String trainFile, String testFile) {
		this.trainFile = trainFile;
		this.testFile = testFile;

	}

	protected abstract TaHmmSampleCursor newCursor(TaTohlcvCursor cursor);

	public void startTest() {

		TaTohlcvCursor cursor = TaFileTimeBarsCursor.valueOf(new File(
				TaTestData.dataHomeFile(), this.trainFile));
		TaHmmSampleCursor sc = this.newCursor(cursor);
		TaHmmTrainner trainer = new TaHmmTrainner(sc);
		trainer.pseudoCount(0.5);
		trainer.hiddenStates(sc.getTotalHiddenStates());
		trainer.outputStates(sc.getTotalOutputStates());
		HmmModel model = trainer.start();
		System.out.print("model.getTransitionMatrix:");
		System.out.print(model.getTransitionMatrix());
		System.out.print("model.getEmissionMatrix:");
		System.out.print(model.getEmissionMatrix());
		
		
		TaTohlcvCursor cursor2 = TaFileTimeBarsCursor.valueOf(new File(
				TaTestData.dataHomeFile(), this.testFile));
		TaHmmSampleCursor sc2 = this.newCursor(cursor2);
		TaHmmEvaluator ev = TaHmmEvaluator.newInstance(model);
	
		ev.evaluate(sc2);//
		
		model.toString();
	}

	
}
