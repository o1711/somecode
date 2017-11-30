package org.ta.director.hmm;

import org.ta.director.classifier.TaC1Classifier;
import org.ta.trader.hista.tohlcv.TaTohlcvCursor;

public class TaHmmPlain3SampleCursor extends TaBaseHmmSampleCursor {

	private static int HIDDENS = 3;
	private static int MARGIN_RIGHT = 1;
	private static int BACKWORDS = 5;
	private static int MARGIN_LEFT = BACKWORDS - 1;
	private static int FORWARDS = 1;
	public static int DENSITY = 5;
	private static int OUTPUTS = (int) Math.pow(4, DENSITY);
	private TaC1Classifier classifier;

	public TaHmmPlain3SampleCursor(int sequenceSize, TaTohlcvCursor cursor) {
		super(OUTPUTS, HIDDENS, sequenceSize, 14+MARGIN_LEFT + sequenceSize + MARGIN_RIGHT, cursor);
		this.classifier = new TaC1Classifier(DENSITY,this.window);

	}

	@Override
	public boolean next() {
		boolean rt = super.next();
		if (!rt) {
			return rt;
		}

		return rt;
	}

	@Override
	protected int getObserved(int idx) {
		int j = this.sequenceSize - 1 - idx + MARGIN_RIGHT;
		int rt = this.classifier.classify(j);
		if (rt == 328) {
			//this.window.get(j).println(System.out);
		}
		return rt;
	}

	@Override
	protected int getHidden(int i) {
		int j = this.sequenceSize - 1 - i + MARGIN_RIGHT - FORWARDS;
		double delta = this.window.getClose(j) - this.window.getOpen(j);
		return delta > 0 ? 0 : (delta < 0 ? 1 : 2);
	}

}
