/**
 * 
 */
package com.graphscape.commons.util;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

/**
 * @author wuzhen
 * 
 */
public class StreamUtil {
	public static final int BUFFER_SIZE = 1024 * 8;

	public static int copy(Reader in, Writer out) throws IOException {
		try {
			int byteCount = 0;
			char[] buffer = new char[BUFFER_SIZE];
			int bytesRead;
			while ((bytesRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
				byteCount += bytesRead;
			}
			out.flush();
			return byteCount;
		} finally {
			try {
				in.close();
			} catch (IOException ex) {
				// do nothing
			}
			try {
				out.close();
			} catch (IOException ex) {
				// do nothing
			}
		}
	}

	public static String copyToString(Reader in) throws IOException {
		StringWriter out = new StringWriter();
		copy(in, out);
		return out.toString();
	}

}
