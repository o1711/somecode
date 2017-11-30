package org.ta.director.hmm;

import org.ta.common.cursor.TaCursor;

public interface TaHmmSampleCursor extends TaCursor<TaHmmSample> {

	public int getTotalOutputStates();

	public int getTotalHiddenStates();

}
