package org.ta.trader.hista.tohlcv;

import org.ta.common.cursor.TaCursor;
/**
 * TODO TaCursor<TaTohlcv>
 * @author wu
 *
 */
public interface TaTohlcvCursor extends TaCursor<TaTohlcv> {

	public boolean nextTime(long time, boolean strict);

}
