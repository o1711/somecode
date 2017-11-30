package org.ta.common.cursor;

import org.ta.trader.hista.tohlcv.TaTohlcv;
import org.ta.trader.hista.tohlcv.TaTohlcvCursor;

public abstract class TaProxyCursor<T1> implements TaCursor<T1> {

	protected TaTohlcvCursor target;

	public TaProxyCursor(TaTohlcvCursor cursor) {
		this.target = cursor;
	}

	@Override
	public boolean next() {
		return this.target.next();
	}

	@Override
	public boolean previous() {
		return this.target.previous();
	}

	@Override
	public boolean last() {
		return this.target.last();
	}

	@Override
	public boolean first() {
		return this.target.first();
	}

	@Override
	public void afterLast() {
		this.target.afterLast();
	}

	@Override
	public void beforeFirst() {
		this.target.beforeFirst();
	}

}
