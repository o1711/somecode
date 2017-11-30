package org.ta.director.hmm;

import org.ta.common.util.TaTimeUtil;
import org.ta.director.classifier.TaC1Classifier;
import org.ta.trader.hista.tohlcv.TaTohlcvCursor;

public class TaHmmPeak1SampleCursor extends TaBaseHmmSampleCursor {

	private static int PEAKSCOPE = 10;
	private static int HIDDENS = 4;
	private static int MARGIN_RIGHT = PEAKSCOPE;
	private static int BACKWORDS = 5;
	private static int MARGIN_LEFT = BACKWORDS - 1;
	public static int DENSITY = 5;
	private static int OUTPUTS = (int) Math.pow(4, DENSITY);
	private TaC1Classifier classifier;

	public TaHmmPeak1SampleCursor(int sequenceSize, TaTohlcvCursor cursor) {
		super(OUTPUTS, HIDDENS, sequenceSize, 14 + MARGIN_LEFT + sequenceSize
				+ MARGIN_RIGHT, cursor);
		this.classifier = new TaC1Classifier(DENSITY, this.window);

	}

	@Override
	protected int getObserved(int idx) {
		int j = this.sequenceSize - 1 - idx + MARGIN_RIGHT;
		int rt = this.classifier.classify(j);
		return rt;
	}

	@Override
	protected int getHidden(int idx) {

		int j = this.sequenceSize - 1 - idx + MARGIN_RIGHT;
		boolean peakUp = true;
		boolean peakDown = true;
		long time = this.window.getTime(j);
		if(time == TaTimeUtil.parse("2015.04.08 06.45.00")){
			System.out.println("");//
		}
		double high = this.window.getHigh(j);
		double low = this.window.getLow(j);
		for (int i = 0; i < PEAKSCOPE; i++) {
			int k = j + i + 1;
			int l = j - i - 1;

			if (peakUp) {
				double highK = this.window.getHigh(k);
				double highL = this.window.getHigh(l);
				if (highK > high || highL > high) {
					peakUp = false;
				}
			}

			if (peakDown) {

				double lowK = this.window.getLow(k);
				double lowL = this.window.getLow(l);
				if (lowK < low || lowL < low) {
					peakDown = false;
				}
			}
		}
		boolean peakBoth = (peakUp && peakDown);
		return peakBoth ? 0 : (peakUp ? 1 : (peakDown ? 2 : 3));

	}

	@Override
	public Object getUserObject() {
		int j = MARGIN_RIGHT;
		return this.window.get(j);
	}

}
