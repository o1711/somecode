package org.ta.trader.executing;

import org.ta.common.data.TaObjectData;
import org.ta.trader.TaSymbol;

public class TaOrderOpenCommand extends TaOrderCommand {
	public TaSymbol symbol;
	public TaOrderType otype;
	public double lots;
	public double price;
	public double sl;
	public double tp;
	public String magic;

	public static TaObjectData toData(TaOrderOpenCommand o) {
		TaObjectData odI = new TaObjectData();
		odI.property("orderSymbol", o.symbol.getStringValue());//
		odI.property("orderType", o.otype.name());
		odI.property("orderPrice", o.price);
		odI.property("orderLots", o.lots);
		odI.property("orderStoploss", o.sl);
		odI.property("orderTakeprofit", o.tp);
		odI.property("magic", o.magic);
		return odI;
	}

	public static TaOrderOpenCommand parseData(TaObjectData order) {
		TaSymbol symbol = order.getPropertyAsSymbol("orderSymbol");
		TaOrderType otype = order.getPropertyAsOrderType("orderType");
		double price = order.getPropertyAsDouble("orderPrice");
		double sl = order.getPropertyAsDouble("orderStoploss");
		double tp = order.getPropertyAsDouble("orderTakeprofit");
		double lots = order.getPropertyAsDouble("orderLots");
		String magic = order.getPropertyAsString("magic");
		TaOrderOpenCommand rt = new TaOrderOpenCommand().symbol(symbol).otype(otype).price(price).lots(lots)
				.sl(sl).tp(tp).magic(magic);

		return rt;
	}
	
	public TaOrderOpenCommand symbol(TaSymbol symbol) {
		this.symbol = symbol;
		return this;
	}

	public TaOrderOpenCommand magic(String magic) {
		this.magic = magic;
		return this;
	}

	public TaOrderOpenCommand otype(TaOrderType otype) {
		this.otype = otype;
		return this;
	}

	public TaOrderOpenCommand lots(double lots) {
		this.lots = lots;
		return this;
	}

	public TaOrderOpenCommand price(double price) {
		this.price = price;
		return this;
	}

	public TaOrderOpenCommand sl(double sl) {
		this.sl = sl;
		return this;
	}

	public TaOrderOpenCommand tp(double tp) {
		this.tp = tp;
		return this;
	}

}
