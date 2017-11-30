/**
 *  
 */
package com.graphscape.commons.util;

import java.io.IOException;
import java.io.Reader;
import java.util.Collection;
import java.util.Random;

import com.graphscape.commons.lang.GsException;

/**
 * @author wu
 * 
 */
public class StringUtil {
	private static Random random = new Random();

	public static String randomString(int maxSize) {
		int size = random.nextInt(maxSize);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < size; i++) {
			char c = (char) random.nextInt();
			sb.append(c);
		}
		return sb.toString();

	}

	public static String toString(Collection<String> sc, char sep) {

		if (sc.isEmpty()) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		int i = 0;
		for (String s : sc) {

			sb.append(s);
			if (i + 1 < sc.size()) {
				sb.append(sep);
			}
		}

		return sb.toString();
	}

	public static String fillSpace(String str, int totalLength) {
		int size = totalLength - str.length();
		if (size <= 0) {
			return str;
		}
		return str + space(size);//

	}

	public static String fillSpace(int totalLength, String str) {
		int size = totalLength - str.length();
		if (size <= 0) {
			return str;
		}
		return space(size) + str;//

	}

	public static String space(String str) {
		return space(str.length());
	}

	public static String space(int size) {
		return string(' ', size);
	}

	public static String string(char c, int size) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < size; i++) {
			sb.append(c);
		}
		return sb.toString();
	}

	public static String readAsString(Reader reader) {
		StringBuffer sb = new StringBuffer();
		char[] cbuf = new char[1024];
		while (true) {
			try {
				int is = reader.read(cbuf);
				if (is <= 0) {
					break;
				}
				sb.append(cbuf, 0, is);
			} catch (IOException e) {
				throw new GsException(e);
			}

		}
		return sb.toString();
	}

	public static int maxLength(String hex0, String hex2, String hex1) {
		return Math.max(hex0.length(), Math.max(hex1.length(), hex2.length()));

	}
}
