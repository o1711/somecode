package org.ta.trader.hista.tohlcv;

import java.io.File;

public class TaFileTimeBarsCursor extends TaBaseTohlcv implements
		TaTohlcvCursor {

	private int index = -1;
	private TaTimeBarsFile table;

	public static TaFileTimeBarsCursor valueOf(File file) {
		TaTimeBarsFile f = TaTimeBarsFile.valueOf(file);
		return new TaFileTimeBarsCursor(f);
	}

	public TaFileTimeBarsCursor(TaTimeBarsFile table) {
		this.table = table;
	}

	@Override
	public boolean nextTime(long time, boolean strict) {

		while (this.next()) {

			if (this.getTime() == time) {
				return true;
			}
			if (this.getTime() > time) {
				if (strict) {
					return false;
				} else {
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public boolean next() {
		int next = this.index + 1;
		if (next >= this.table.size()) {
			this.index = this.table.size();
			return false;
		}
		this.index = next;
		return true;
	}

	public boolean isEmpty() {
		return this.table.isEmpty();
	}

	@Override
	public boolean previous() {
		if (this.isEmpty()) {
			return false;
		}
		int next = this.index - 1;

		if (next < 0) {
			this.index = -1;
			return false;
		}

		this.index = next;
		return true;
	}

	@Override
	public long getTime() {
		return this.table.getRow(index).getTime();
	}

	@Override
	public double getOpen() {
		return this.table.getRow(index).getOpen();
	}

	@Override
	public double getHigh() {
		return this.table.getRow(index).getHigh();
	}

	@Override
	public double getLow() {
		return this.table.getRow(index).getLow();
	}

	@Override
	public double getClose() {
		return this.table.getRow(index).getClose();
	}

	@Override
	public double getVolume() {
		return this.table.getRow(index).getVolume();
	}

	@Override
	public boolean last() {
		this.afterLast();
		return this.previous();
	}

	@Override
	public void afterLast() {
		this.index = this.table.size();
	}

	@Override
	public boolean first() {
		if (this.isEmpty()) {
			return false;
		}
		this.index = 0;
		return true;
	}

	@Override
	public void beforeFirst() {
		this.index = -1;
	}

	@Override
	public TaTohlcv get() {

		return this;
	}

}
