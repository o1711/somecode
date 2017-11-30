package org.ta.trader.executing;

import java.util.ArrayList;
import java.util.List;

import org.ta.common.data.TaArrayData;
import org.ta.common.data.TaObjectData;
import org.ta.trader.TaSymbol;
import org.ta.trader.TaSide;

public class TaOrderInfo {

	private String id;

	private TaSymbol symbol;

	private TaOrderType orderType;
	
	private TaSide direction;

	private double lots;

	private double price;

	private double sl;

	private double tp;

	private String magic;

	private boolean closed;

	private double closePrice;

	public TaOrderInfo() {

	}

	public static TaObjectData toData(TaOrderInfo oi) {
		TaObjectData rt = new TaObjectData();

		rt.property("id", oi.id);
		rt.property("magic", oi.magic);//
		rt.property("symbol", oi.symbol.getStringValue());
		rt.property("orderType", oi.orderType.name());
		rt.property("lots", oi.lots);
		rt.property("price", oi.price);
		rt.property("sl", oi.sl);
		rt.property("tp", oi.tp);
		return rt;
	}

	public static TaArrayData toData(List<TaOrderInfo> oil) {
		TaArrayData rt = new TaArrayData();
		for (TaOrderInfo oi : oil) {
			rt.add(toData(oi));
		}
		return rt;
	}

	public static TaOrderInfo parseData(TaObjectData oA) {
		TaOrderInfo rt = new TaOrderInfo();
		rt.id = oA.getPropertyAsString("id", true);
		rt.magic = oA.getPropertyAsString("magic", true);
		rt.symbol = oA.getPropertyAsSymbol("symbol", true);
		rt.orderType = oA.getPropertyAsOrderType("orderType", true);
		rt.lots = oA.getPropertyAsDouble("lots");
		rt.price = oA.getPropertyAsDouble("price");
		rt.sl = oA.getPropertyAsDouble("sl");
		rt.tp = oA.getPropertyAsDouble("tp");
		return rt;
	}

	public static List<TaOrderInfo> parseData(TaArrayData oiA) {
		List<TaOrderInfo> rt = new ArrayList<TaOrderInfo>();
		for (int i = 0; i < oiA.size(); i++) {
			TaObjectData oI = (TaObjectData) oiA.get(i);
			rt.add(parseData(oI));
		}

		return rt;
	}
	
	public double getClosePrice() {
		return closePrice;
	}

	public void close(double closePrice) {
		this.closePrice = closePrice;
		this.closed = true;
	}

	public void copy(TaOrderInfo oi) {
		this.id = oi.id;
		this.symbol = oi.symbol;
		this.orderType = oi.orderType;
		this.lots = oi.lots;
		this.price = oi.price;
		this.sl = oi.sl;
		this.tp = oi.tp;
	}

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	public String getId() {
		return this.id;
	}

	public TaSymbol getSymbol() {
		return symbol;
	}

	public TaOrderInfo symbol(TaSymbol symbol) {
		this.symbol = symbol;
		return this;
	}

	public TaOrderType getOrderType() {
		return orderType;
	}

	public TaSide getDirection() {
		return direction;
	}

	public void setDirection(TaSide orderSide) {
		this.direction = orderSide;
	}

	public TaOrderInfo orderType(TaOrderType orderType) {
		this.orderType = orderType;
		return this;
	}

	public double getLots() {
		return lots;
	}

	public TaOrderInfo lots(double lots) {
		this.lots = lots;
		return this;
	}

	public double getPrice() {
		return price;
	}

	public TaOrderInfo price(double price) {
		this.price = price;
		return this;
	}

	public double getSl() {
		return sl;
	}

	public TaOrderInfo sl(double sl) {
		this.sl = sl;
		return this;
	}

	public double getTp() {
		return tp;
	}

	public TaOrderInfo tp(double tp) {
		this.tp = tp;
		return this;
	}

	@Override
	public String toString() {
		return "id:" + id + ",symbol:" + this.symbol + ",otype:" + this.orderType + ",price:" + price
				+ ",lots:" + lots + ",sl:" + sl + ",tp:" + tp + ",magic:" + magic + ",closed:" + this.closed;
	}

	public String getMagic() {
		//
		return this.magic;
	}

	public TaOrderInfo id(String id) {
		//
		this.id = id;
		return this;
	}

	public TaOrderInfo magic(String magic) {
		//
		this.magic = magic;
		return this;
	}

}
