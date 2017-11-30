/**
 * Dec 14, 2013
 */
package com.graphscape.commons.util;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import com.graphscape.commons.lang.GsException;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class ReaderUtil {

	public static Reader toReader(StringBuffer sb) {
		return new StringReader(sb.toString());
	}

	public static StringBuffer readAsStringBuffer(Reader reader) {
		StringBuffer rt = new StringBuffer();
		try {
			while (true) {
				char[] buffer = new char[8192];
				int len = reader.read(buffer);
				if (len == -1) {
					break;
				}
				rt.append(buffer, 0, len);

			}
		} catch (IOException e) {
			throw GsException.toRuntimeException(e);
		}
		return rt;

	}

}
