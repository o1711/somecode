package org.ta.director.hmm;

import org.ta.trader.hista.tohlcv.TaTohlcvCursor;

public class TaHmmPlain2SampleCursor extends TaBaseHmmSampleCursor {

	private static int HIDDENS = 3;
	private static int MARGIN_RIGHT = 1;
	private static int BACKWORDS = 5;
	private static int MARGIN_LEFT = BACKWORDS - 1;
	private static int FORWARDS = 1;

	public TaHmmPlain2SampleCursor(int sequenceSize, TaTohlcvCursor cursor) {
		super(3, HIDDENS, sequenceSize, MARGIN_LEFT + sequenceSize
				+ MARGIN_RIGHT, cursor);

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
		int ups = 0;
		int downs = 0;
		for (int i = 0; i < BACKWORDS; i++) {
			double delta = this.window.getClose(j) - this.window.getOpen(j);
			if (delta > 0) {
				ups++;
			} else {
				downs++;
			}
			j++;
		}

		return (ups == BACKWORDS) ? 0 : (downs == BACKWORDS ? 1 : 2);

	}

	@Override
	protected int getHidden(int i) {
		int j = this.sequenceSize - 1 - i + MARGIN_RIGHT - FORWARDS;
		double delta = this.window.getClose(j) - this.window.getOpen(j);
		return delta > 0 ? 0 : (delta < 0 ? 1 : 2);
	}

}
