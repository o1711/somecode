package org.ta.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import org.ta.common.config.TaException;

public class TaFileUtil {

	public static void copy(InputStream from, File to) throws IOException {

		FileOutputStream fw = new FileOutputStream(to);
		while (true) {
			int c = from.read();
			if (c == -1) {
				break;
			}
			fw.write(c);
		}
		fw.close();
	}

	public static String read(Reader reader) {
		StringBuffer sb = new StringBuffer();
		while (true) {

			try {
				int c = reader.read();
				if (c == -1) {
					break;
				}
				sb.append((char) c);
			} catch (IOException e) {
				throw new TaException(e);
			}

		}
		return sb.toString();
	}
}
