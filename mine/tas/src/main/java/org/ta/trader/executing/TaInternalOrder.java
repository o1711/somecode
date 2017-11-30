package org.ta.trader.executing;

import org.ta.trader.TaSymbol;
import org.ta.trader.TaSide;

public class TaInternalOrder {

	private TaOrderInfo orderInfo;

	private String magic;

	private String advisorId;

	private TaSymbol symbol;

	private double risk;

	private double lots;

	private double price;

	private TaOrderType orderType;

	private double sl;

	private double tp;

	private TaSide direction;
	
	public TaInternalOrder(TaSymbol symbol,TaSide dir) {
		this.symbol = symbol;
		this.direction = dir;
	}

	public TaOrderInfo getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(TaOrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}

	public String getMagic() {
		return magic;
	}

	public void setMagic(String magic) {
		this.magic = magic;
	}

	public String getAdvisorId() {
		return advisorId;
	}

	public void setAdvisorId(String advisorId) {
		this.advisorId = advisorId;
	}

	public TaSymbol getSymbol() {
		return symbol;
	}

	public void setSymbol(TaSymbol symbol) {
		this.symbol = symbol;
	}

	public double getRisk() {
		return risk;
	}

	public void setRisk(double risk) {
		this.risk = risk;
	}

	public double getLots() {
		return lots;
	}

	public void setLots(double lots) {
		this.lots = lots;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public TaOrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(TaOrderType orderType) {
		this.orderType = orderType;
		
	}

	public double getSl() {
		return sl;
	}

	public void setSl(double sl) {
		this.sl = sl;
	}

	public double getTp() {
		return tp;
	}

	public void setTp(double tp) {
		this.tp = tp;
	}

	public TaSide getDirection() {
		return direction;
	}

	public void setDirection(TaSide direction) {
		this.direction = direction;
	}


	public TaInternalOrder direction(TaSide dir) {
		this.direction = dir;
		return this;
	}

	public TaInternalOrder risk(double risk) {
		this.risk = risk;
		return this;
	}

	public TaInternalOrder lots(double lots) {
		this.lots = lots;
		return this;
	}

	public TaInternalOrder advisorId(String advisorId) {
		this.advisorId = advisorId;
		return this;
	}

	public TaInternalOrder magic(String id) {
		this.magic = id;
		return this;
	}

	public void orderInfo(TaOrderInfo oi) {
		this.orderInfo = oi;
	}

	public double getOrderRisk() {
		return this.risk;
	}

	public double getOrderlLots() {
		return this.lots;
	}

	public double getOrderProfitForPrice(double price) {
		double openPrice = this.orderInfo.getPrice();
		if (this.direction.isUp()) {
			return price - openPrice;
		} else {
			return openPrice - price;
		}
	}

	public double getOrderStopLoss() {
		return this.orderInfo.getSl();
	}

	public double getOrderTakeProfit() {
		return this.orderInfo.getTp();
	}

	public boolean isOrderProfitOfRisk(double currentPrice,
			double originalRisk, double factor) {
		double stoploss = this.orderInfo.getSl();
		double openPrice = this.orderInfo.getPrice();
		double riskFactor = originalRisk * factor;
		if (this.direction.isUp()) {
			return currentPrice >= stoploss + originalRisk + riskFactor;
		} else {
			return currentPrice <= stoploss - originalRisk - riskFactor;
		}
	}

	public boolean isOrderProfitHalfRisk(double currentPrice,
			double originalRisk) {
		return isOrderProfitOfRisk(currentPrice, originalRisk, 1.0 / 2.0);
	}

	public boolean isOrderProfitOneRisk(double currentPrice, double originalRisk) {
		return isOrderProfitOfRisk(currentPrice, originalRisk, 1.0);
	}

	public boolean isOrderUp() {
		//
		return this.direction.isUp();
	}
}
