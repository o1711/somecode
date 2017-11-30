package org.ta.client;

import org.ta.common.data.TaArrayData;
import org.ta.common.data.TaObjectData;
import org.ta.trader.hista.tohlcv.TaTohlcv;
import org.ta.trader.hista.tohlcv.TaTohlcvCursor;

public class TaTimeBarsInitRequester extends TaBaseRequester {

	private TaArrayData timebarArray;

	public TaTimeBarsInitRequester(TaClient client) {
		super(client, "timebars");
		this.timebarArray = new TaArrayData();
		this.request.set("timebarArray", timebarArray);

	}

	public TaTimeBarsInitRequester addAll(TaTohlcvCursor cursor) {
		while (cursor.next()) {
			this.add(-1,cursor.get());
		}
		return this;
	}
	public TaTimeBarsInitRequester add(TaTohlcv tohlcv) {
		return add(-1,tohlcv);
	}
	public TaTimeBarsInitRequester add(int idx,TaTohlcv tohlcv) {
		TaObjectData oI = new TaObjectData();
		oI.propertyTime("time", tohlcv.getTime()); //
		oI.property("high", tohlcv.getHigh());
		oI.property("low", tohlcv.getLow());
		oI.property("open", tohlcv.getOpen());
		oI.property("close", tohlcv.getClose());
		oI.property("volume", tohlcv.getVolume());
		this.timebarArray.add(idx, oI);

		return this;
	}

}
