package org.ta.trader.hista.tohlcv;

import java.util.ArrayList;
import java.util.List;

public class TaTohlcvWindowImpl extends TaBaseTohlcvWindow {

	private List<TaTohlcvObject> tohlcvList;

	private TaTohlcvWindowListeners listeners = new TaTohlcvWindowListeners();

	private int maxWidth;

	public TaTohlcvWindowImpl() {
		this(-1);
	}

	public TaTohlcvWindowImpl(int width) {
		this.maxWidth = width;
		this.tohlcvList = new ArrayList<TaTohlcvObject>();
	}

	@Override
	public int size() {
		return this.tohlcvList.size();
	}

	@Override
	public TaTohlcv get() {
		return this.get(0);
	}

	@Override
	public TaTohlcv get(int idx) {
		if (idx >= this.tohlcvList.size()) {
			return null;
		}
		return this.tohlcvList.get(idx);//
	}

	public boolean add(int index, TaTohlcvObject tbar) {
		if (this.maxWidth != -1 && index >= this.maxWidth) {
			return false;// ignore
		}

		this.tohlcvList.add(index, tbar);
		if (this.maxWidth != -1) {
			while (this.tohlcvList.size() > this.maxWidth) {
				this.tohlcvList.remove(this.tohlcvList.size() - 1);
			}
		}
		return true;
	}

	@Override
	public void update(long time, double high, double open, double close,
			double low, double volume) {
		int idx = -1;
		TaTohlcvObject timeBar = null;
		for (int i = 0; i < tohlcvList.size(); i++) {
			TaTohlcvObject timeBarI = this.tohlcvList.get(i);

			long timeI = timeBarI.getTime();
			if (timeI == time) {
				timeBar = timeBarI;
				break;
			}

			if (time > timeI) {
				idx = i;//
				break;
			}
		}

		if (timeBar != null) {
			timeBar.set(timeBar.getTime(), high, open, close, low, volume);
		} else {
			timeBar = new TaTohlcvObject().set(time, high, open, close, low,
					volume);
			if (idx == -1) {// not found,append as the last one.
				idx = this.tohlcvList.size() - 1;
			}
			if(idx ==-1){
				idx = 0;
			}

			this.add(idx, timeBar);

		}
		this.listeners.onUpdate(this, idx, timeBar);//
	}

	@Override
	public void addListener(TaTohlcvWindowListener l) {
		this.listeners.add(l);
	}

	@Override
	public int getMaxWidth() {
		return this.maxWidth;
	}

}
