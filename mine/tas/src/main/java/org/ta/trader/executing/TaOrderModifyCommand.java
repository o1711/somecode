package org.ta.trader.executing;

import org.ta.common.data.TaObjectData;

public class TaOrderModifyCommand extends TaOrderCommand {
	public String orderId;
	public double lots;
	public double price;
	public double sl;
	public double tp;

	public static TaObjectData toData(TaOrderModifyCommand o) {
		TaObjectData odI = new TaObjectData();

		odI.property("orderId", o.orderId);
		odI.property("orderPrice", o.price);
		odI.property("orderLots", o.lots);
		odI.property("orderStoploss", o.sl);
		odI.property("orderTakeprofit", o.tp);
		return odI;
	}

	public static TaOrderModifyCommand parseData(TaObjectData order) {
		String orderId = order.getPropertyAsString("orderId", true);
		double price = order.getPropertyAsDouble("orderPrice");
		double sl = order.getPropertyAsDouble("orderStoploss");
		double tp = order.getPropertyAsDouble("orderTakeprofit");
		double lots = order.getPropertyAsDouble("orderLots");
		TaOrderModifyCommand rt = new TaOrderModifyCommand().orderId(orderId)
				.price(price).lots(lots).sl(sl).tp(tp);

		return rt;
	}

	public TaOrderModifyCommand orderId(String orderId) {
		this.orderId = orderId;
		return this;
	}

	public TaOrderModifyCommand lots(double lots) {
		this.lots = lots;
		return this;
	}

	public TaOrderModifyCommand price(double price) {
		this.price = price;
		return this;
	}

	public TaOrderModifyCommand sl(double sl) {
		this.sl = sl;
		return this;
	}

	public TaOrderModifyCommand tp(double tp) {
		this.tp = tp;
		return this;
	}

}
