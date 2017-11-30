package org.ta.trader.hista.tohlcv;

import java.io.IOException;
import java.io.Writer;

public interface TaTohlcvWindow {

	public TaTohlcv get(int idx);
	
	//-1: no limit.
	public int getMaxWidth();
	
	public int size();

	public TaTohlcv get();

	public long getTime();

	public double getOpen();

	public double getHigh();

	public double getLow();

	public double getClose();

	public double getVolume();

	public long getTime(int idx);

	public double getOpen(int idx);

	public double getHigh(int idx);

	public double getLow(int idx);

	public double getClose(int idx);

	public double getVolume(int idx);
	
	public void println(Writer writer) throws IOException;

	public void updateAll(TaTohlcvCursor cursor);
	
	public void updateAll(TaTohlcvCursor cursor, long fromTime, long toTime);

	public void update(TaTohlcv toh);
	
	public void update(long time, double open, double high, double low,
			double close, double volume);
	
	public void addListener(TaTohlcvWindowListener l);

}
