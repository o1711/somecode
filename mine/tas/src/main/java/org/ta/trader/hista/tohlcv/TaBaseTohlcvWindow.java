package org.ta.trader.hista.tohlcv;

import java.io.IOException;
import java.io.Writer;

public abstract class TaBaseTohlcvWindow implements TaTohlcvWindow {

	@Override
	public TaTohlcv get() {
		//
		return this.get(0);
	}

	@Override
	public long getTime() {

		return this.getTime(0);
	}

	@Override
	public double getOpen() {
		//
		return this.getOpen(0);
	}

	@Override
	public double getHigh() {
		//
		return this.getHigh(0);
	}

	@Override
	public double getLow() {
		//
		return this.getLow(0);
	}

	@Override
	public double getClose() {
		//
		return this.getClose(0);
	}

	@Override
	public double getVolume() {
		//
		return this.getVolume(0);
	}

	@Override
	public long getTime(int idx) {
		//
		return this.get(idx).getTime();
	}

	@Override
	public double getOpen(int idx) {
		//
		return this.get(idx).getOpen();
	}

	@Override
	public double getHigh(int idx) {
		//
		return this.get(idx).getHigh();
	}

	@Override
	public double getLow(int idx) {
		//
		return this.get(idx).getLow();
	}

	@Override
	public double getClose(int idx) {
		//
		return this.get(idx).getClose();
	}

	@Override
	public double getVolume(int idx) {
		//
		return this.get(idx).getVolume();
	}
	//TODO move update to TaCursor interface.
	@Override
	public void update(TaTohlcv toh) {

		this.update(toh.getTime(), toh.getOpen(), toh.getHigh(), toh.getLow(),
				toh.getClose(), toh.getVolume());
	}

	@Override
	public void updateAll(TaTohlcvCursor cursor) {
		while (cursor.next()) {
			this.update((TaTohlcv) cursor);//
		}
	}

	@Override
	public void updateAll(TaTohlcvCursor cursor, long fromTime, long toTime) {

		if (!cursor.nextTime(fromTime, false)) {
			return;
		}

		while (true) {
			long timeI = cursor.get().getTime();
			if (timeI > toTime) {
				break;
			}
			this.update((TaTohlcv) cursor);//

			if (!cursor.next()) {
				break;
			}
		}
	}

	@Override
	public void println(Writer writer) throws IOException {
		for (int i = 0; i < this.size(); i++) {
			this.get(i).println(writer);//
		}
	}
}
