package org.ta.trader.hista.tohlcv;


public class TaProxyTohlcvWindow extends TaBaseTohlcvWindow implements TaTohlcvWindow {

	protected TaTohlcvWindow target;

	public TaProxyTohlcvWindow(TaTohlcvWindow target){
		this.target = target;
	}
	
	@Override
	public int size(){
		return this.target.size();
	}
	@Override
	public TaTohlcv get(int idx) {
		return this.target.get(idx);
	}

	@Override
	public TaTohlcv get() {
		//
		return this.target.get();
	}

	@Override
	public long getTime() {

		return this.target.getTime();
	}

	@Override
	public double getOpen() {
		//
		return this.target.getOpen();
	}

	@Override
	public double getHigh() {
		//
		return this.target.getHigh();
	}

	@Override
	public double getLow() {
		//
		return this.target.getLow();
	}

	@Override
	public double getClose() {
		//
		return this.target.getClose();
	}

	@Override
	public double getVolume() {
		//
		return this.target.getVolume();
	}

	@Override
	public long getTime(int idx) {
		//
		return this.target.getTime(idx);
	}

	@Override
	public double getOpen(int idx) {
		//
		return this.target.getOpen(idx);
	}

	@Override
	public double getHigh(int idx) {
		//
		return this.target.getHigh(idx);
	}

	@Override
	public double getLow(int idx) {
		//
		return this.target.getLow(idx);
	}

	@Override
	public double getClose(int idx) {
		//
		return this.target.getClose(idx);
	}

	@Override
	public double getVolume(int idx) {
		//
		return this.target.getVolume(idx);
	}
	@Override
	public void update(long time, double high, double open, double close,
			double low, double volume) {
		this.target.update(time, high, open, close, low, volume);
		
	}

	@Override
	public void updateAll(TaTohlcvCursor cursor, long fromTime, long toTime) {
		this.target.updateAll(cursor, fromTime, toTime);//
	}

	@Override
	public void addListener(TaTohlcvWindowListener l) {
		this.target.addListener(l);//
	}

	@Override
	public int getMaxWidth() {
		return this.target.getMaxWidth();
	}

}
