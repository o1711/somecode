package org.ta.director.hmm;

import org.ta.common.config.TaException;
import org.ta.common.cursor.TaProxyCursor;
import org.ta.trader.hista.tohlcv.TaTohlcvCursor;
import org.ta.trader.hista.tohlcv.TaTohlcvWindow;
import org.ta.trader.hista.tohlcv.TaTohlcvWindowImpl;

public abstract class TaBaseHmmSampleCursor extends TaProxyCursor<TaHmmSample>
		implements TaHmmSampleCursor,TaHmmSample {

	protected int sequenceSize;
	
	protected TaTohlcvWindow window;

	protected int totalOutputStates;

	protected int totalHiddenStates;

	protected int windowWidth;
	
	public TaBaseHmmSampleCursor(int toutputs, int thiddens,
			int sequenceSize, int windowWidth, TaTohlcvCursor cursor) {
		super(cursor);
		this.totalOutputStates = toutputs;
		this.totalHiddenStates = thiddens;
		this.sequenceSize = sequenceSize;
		this.windowWidth = windowWidth;
		this.window = new TaTohlcvWindowImpl(this.windowWidth);

		for (int i = 0; i < this.windowWidth; i++) {
			if (!cursor.next()) {
				throw new TaException("no enough data.");
			}
			this.window.update(cursor.get());
		}
	}

	@Override
	public boolean next() {

		boolean rt = super.next();
		if (rt) {
			this.window.update(this.target.get());//			
		}
		return rt;

	}

	@Override
	public int getTotalOutputStates() {
		return this.totalOutputStates;
	}

	@Override
	public int getTotalHiddenStates() {
		return this.totalHiddenStates;
	}
	
	@Override
	public TaHmmSample get(){
		return this;
	}
	
	@Override
	public int[] getObserved() {
		int[] rt = new int[this.sequenceSize];
		for (int i = 0; i < this.sequenceSize; i++) {
			rt[i] = this.getObserved(i);
		}
		return rt;
	}

	@Override
	public int[] getHidden() {
		int[] rt = new int[this.sequenceSize];		
		for (int i = 0; i < this.sequenceSize; i++) {
			rt[i] = this.getHidden(i);
		}
		return rt;
	}

	protected abstract int getObserved(int i);

	protected abstract int getHidden(int i);

	@Override
	public Object getUserObject() {
		return null;
	}
	
}
