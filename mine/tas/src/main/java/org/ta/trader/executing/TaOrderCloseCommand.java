package org.ta.trader.executing;

import org.ta.common.data.TaObjectData;

public class TaOrderCloseCommand extends TaOrderCommand {
	public String orderId;
	public double lots;
	public double price;

	public static TaObjectData toData(TaOrderCloseCommand o) {
		TaObjectData odI = new TaObjectData();

		odI.property("orderId", o.orderId);
		odI.property("orderPrice", o.price);
		odI.property("orderLots", o.lots);
		return odI;
	}

	public static TaOrderCloseCommand parseData(TaObjectData order) {
		String orderId = order.getPropertyAsString("orderId", true);
		double price = order.getPropertyAsDouble("orderPrice");
		double lots = order.getPropertyAsDouble("orderLots");
		TaOrderCloseCommand rt = new TaOrderCloseCommand().orderId(orderId)
				.price(price).lots(lots);

		return rt;
	}

	public TaOrderCloseCommand orderId(String orderId) {
		this.orderId = orderId;
		return this;
	}

	public TaOrderCloseCommand lots(double lots) {
		this.lots = lots;
		return this;
	}

	public TaOrderCloseCommand price(double price) {
		this.price = price;
		return this;
	}

}
