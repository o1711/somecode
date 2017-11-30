package org.ta.trader.rating;

import org.ta.trader.TaSymbol;

public class TaRate {

	private double ask;

	private double bid;

	private TaSymbol symbol;

	private long timestamp;

	public TaRate(TaSymbol sym, double ask, double bid) {
		this.symbol = sym;
		this.ask = ask;
		this.bid = bid;
	}

	public TaSymbol getSymbol() {
		return symbol;
	}

	public double getAsk() {
		return this.ask;
	}

	public double getBid() {
		return this.bid;

	}

}
