package org.ta.director.hmm;

import org.ta.trader.hista.tohlcv.TaTohlcvCursor;

public class TaHmmPlain1SampleCursor extends TaBaseHmmSampleCursor {
	static int MARGIN_RIGHT = 1;
	public TaHmmPlain1SampleCursor(int sequenceSize, TaTohlcvCursor cursor) {
		super(3, 3, sequenceSize, sequenceSize + MARGIN_RIGHT, cursor);
	}

	@Override
	protected int getObserved(int i) {
		int j = this.sequenceSize - 1 - i + MARGIN_RIGHT;
		double delta = this.window.getClose(j) - this.window.getOpen(j);
		return delta < 0 ? 0 : (delta == 0 ? 1 : 2);

	}

	@Override
	protected int getHidden(int i) {
		double delta = this.window.getClose(0) - this.window.getOpen(0);
		return delta < 0 ? 0 : (delta == 0 ? 1 : 2);
	}

}
