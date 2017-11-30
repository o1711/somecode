package org.ta.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.ta.common.config.TaException;

public class TaTimeUtil {
	
	public static TaTimeSpan MINITUE = TaTimeSpan.valueOf("60S");
	
	private static SimpleDateFormat FORMAT = new SimpleDateFormat(
			"yyyy.MM.dd HH.mm.ss");

	public static String format(long time) {
		return FORMAT.format(new Date(time));//
	}

	public static long[] parseAll(String[] timeSS) {
		long[] rt = new long[timeSS.length];

		for (int i = 0; i < rt.length; i++) {
			rt[i] = parse(timeSS[i]);
		}

		return rt;
	}

	public static long parse(String timeS) {
		try {
			return FORMAT.parse(timeS).getTime();
		} catch (ParseException e) {
			throw new TaException(e);
		}

	}
}
