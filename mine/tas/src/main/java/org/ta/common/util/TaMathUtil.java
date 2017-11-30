package org.ta.common.util;

import java.util.Arrays;

public class TaMathUtil {

	private static double[] digitsPow = new double[10];
	static {
		for (int i = 0; i < digitsPow.length; i++) {
			digitsPow[i] = Math.pow(10, i);
		}
	}

	public static void roundAll(double[] rt, int digits) {
		for (int i = 0; i < rt.length; i++) {
			rt[i] = round(rt[i], digits);
		}
	}

	public static double round(double rt, int digits) {
		rt = rt * digitsPow[digits];
		rt = Math.round(rt);
		rt = rt / digitsPow[digits];
		return rt;
	}

	public static boolean isSignEquals(double v1, double v2) {
		//
		return v1 > 0 && v2 > 0 || v1 < 0 && v2 < 0;
	}

	public static String toString(int[] values) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < values.length; i++) {
			sb.append(values[i]);
			sb.append(",");
		}
		return sb.toString();
	}

	public static double max(double... ds) {
		double rt = ds[0];
		for (int i = 1; i < ds.length; i++) {
			if (rt < ds[i]) {
				rt = ds[i];
			}
		}

		return rt;
	}
}
