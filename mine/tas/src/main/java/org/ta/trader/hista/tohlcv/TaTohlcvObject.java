package org.ta.trader.hista.tohlcv;



public class TaTohlcvObject extends TaBaseTohlcv {

	private long time;
	private double open;
	private double high;
	private double low;
	private double close;
	private double volume;

	public TaTohlcvObject set(long time, double open, double high, double low, double close, double volume) {
		this.time = time;
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
		this.volume = volume;
		return this;
	}

	@Override
	public long getTime() {
		return this.time;
	}

	@Override
	public double getOpen() {
		return this.open;
	}

	@Override
	public double getHigh() {
		return this.high;
	}

	@Override
	public double getLow() {
		return this.low;
	}

	@Override
	public double getClose() {
		return this.close;
	}

	@Override
	public double getVolume() {
		return this.volume;
	}


}
