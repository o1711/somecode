package org.ta.trader.hista.tohlcv;

import java.io.IOException;
import java.io.PrintStream;
import java.io.Writer;

public interface TaTohlcv {

	public long getTime();

	public double getOpen();

	public double getHigh();

	public double getLow();

	public double getClose();

	public double getVolume();

	
	public StringBuffer appendTo(StringBuffer sb);
	
	//TODO remove print... methods
	public void print(PrintStream writer);
	
	public void println(PrintStream writer);

	public void print(Writer writer) throws IOException;

	public void println(Writer writer) throws IOException;

}
