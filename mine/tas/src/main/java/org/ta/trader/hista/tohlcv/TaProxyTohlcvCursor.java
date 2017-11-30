package org.ta.trader.hista.tohlcv;


public class TaProxyTohlcvCursor implements TaTohlcvCursor {

	private TaTohlcvCursor target;

	private long from;

	private long to;

	public TaProxyTohlcvCursor(TaTohlcvCursor target, long from, long to) {
		this.target = target;
		this.from = from;
		this.to = to;
	}

	@Override
	public boolean next() {
		while (this.target.next()) {
			long timeI = this.target.get().getTime();
			if (timeI >= from && timeI <= to) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean previous() {
		while (this.target.previous()) {
			long timeI = this.target.get().getTime();
			if (timeI >= from && timeI <= to) {
				return true;
			}
		}
		return false;
	}


	@Override
	public boolean nextTime(long time,boolean strict) {
		return this.target.nextTime(time,strict);
	}

	@Override
	public boolean last() {
		return this.target.last();
	}

	@Override
	public void afterLast() {
		this.target.afterLast();
	}

	@Override
	public boolean first() {
		return this.target.first();
	}

	@Override
	public void beforeFirst() {
		this.target.beforeFirst();
	}

	@Override
	public TaTohlcv get() {

		return this.target.get();
	}

}
