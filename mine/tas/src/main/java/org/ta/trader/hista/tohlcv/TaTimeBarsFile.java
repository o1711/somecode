package org.ta.trader.hista.tohlcv;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.ta.common.config.TaException;

import com.opencsv.CSVReader;

public class TaTimeBarsFile {

	private static SimpleDateFormat FORMAT = new SimpleDateFormat(
			"yyyy.MM.dd hh:mm");

	public static class Row {

		private String[] values;

		private Long time;

		private Double[] ohlcv = new Double[7];

		public long getTime() {
			if (this.time != null) {
				return this.time.longValue();
			}
			int i = 0;
			String timeS = values[i++] + " " + values[i++];
			long time;
			try {
				time = FORMAT.parse(timeS).getTime();
			} catch (ParseException e) {
				throw new TaException(e);
			}
			return time;
		}

		public double getDouble(int idx) {
			if (this.ohlcv[idx] != null) {
				return this.ohlcv[idx].doubleValue();
			}
			this.ohlcv[idx] = Double.parseDouble(values[idx]);
			return this.ohlcv[idx].doubleValue();
		}

		public double getOpen() {
			return getDouble(2);
		}

		public double getHigh() {
			return getDouble(3);
		}

		public double getLow() {
			return getDouble(4);
		}

		public double getClose() {
			return getDouble(5);
		}

		public double getVolume() {
			return getDouble(6);
		}

	}

	private List<Row> rowList = new ArrayList<Row>();

	public TaTimeBarsFile() {

	}

	public static TaTimeBarsFile valueOf(File file) {

		try {
			return new TaTimeBarsFile().load(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			throw new TaException(e);
		} catch (IOException e) {
			throw new TaException(e);
		}
	}

	public TaTimeBarsFile load(InputStream is) throws IOException {

		CSVReader reader = new CSVReader(new InputStreamReader(is, "UTF-8"));

		try {
			String[] values = reader.readNext();

			while (values != null) {
				Row row = new Row();
				row.values = values;
				this.rowList.add(row);
				values = reader.readNext();
			}

		} finally {
			reader.close();
		}
		return this;
	}

	public boolean isEmpty() {
		return this.rowList.isEmpty();
	}

	public int size() {
		return this.rowList.size();
	}

	public Row getRow(int idx) {
		return this.rowList.get(idx);
	}

	public TaTohlcvCursor cursor(long from, long to) {
		return new TaProxyTohlcvCursor(this.cursor(), from, to);
	}

	public TaTohlcvCursor cursor() {
		return new TaFileTimeBarsCursor(this);
	}

}
