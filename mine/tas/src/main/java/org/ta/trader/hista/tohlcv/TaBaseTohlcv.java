package org.ta.trader.hista.tohlcv;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Writer;

import org.ta.common.config.TaException;
import org.ta.common.util.TaTimeUtil;

public abstract class TaBaseTohlcv implements TaTohlcv {

	@Override
	public String toString() {
		return this.appendTo(new StringBuffer()).toString();
	}

	@Override
	public StringBuffer appendTo(StringBuffer sb) {
		sb.append(TaTimeUtil.format(this.getTime()));
		sb.append(",");
		sb.append(this.getOpen() + ",\t");
		sb.append(this.getHigh() + ",\t");
		sb.append(this.getLow() + ",\t");
		sb.append(this.getClose() + ",\t");
		sb.append(this.getVolume() + "");
		return sb;
	}

	@Override
	public void println(Writer writer) throws IOException {
		this.print(writer);
		writer.write("\n");
	}

	@Override
	public void print(Writer writer) throws IOException {

		writer.write(TaTimeUtil.format(this.getTime()));
		writer.write(",");
		writer.write(this.getOpen() + ",\t");
		writer.write(this.getHigh() + ",\t");
		writer.write(this.getLow() + ",\t");
		writer.write(this.getClose() + ",\t");
		writer.write(this.getVolume() + "");

	}

	@Override
	public void println(PrintStream writer) {
		this.print(writer);
		writer.println();
	}

	@Override
	public void print(PrintStream writer) {
		Writer writer2 = new PrintWriter(writer);
		try {
			this.print(writer2);
			writer2.flush();
			// writer2.close();
		} catch (IOException e) {
			throw new TaException(e);//
		}
	}

}
