/**
 * Jan 14, 2014
 */
package com.graphscape.commons.file.support;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Writer;

import com.graphscape.commons.file.ByteCursorI;
import com.graphscape.commons.file.ByteFormaterI;
import com.graphscape.commons.file.ByteWindowI;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.record.SerializerI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public abstract class ByteFormaterSupport implements ByteFormaterI {

	public ByteFormaterSupport() {
	}

	@Override
	public void format(ByteCursorI cur, PrintStream ps) {
		Writer writer = new OutputStreamWriter(ps);

		this.format(cur, writer);
		try {
			writer.flush();
		} catch (IOException e) {
			throw GsException.toRuntimeException(e);
		}

	}

	protected String getAsHex(ByteCursorI cur) {
		return this.getAsHex(cur.read());//
	}

	protected String getAsHex(byte[] content) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < content.length; i++) {
			// o/x
			sb.append(String.format("%02x", content[i] & 0xff));
		}
		return sb.toString();
	}

	protected String deserialize(ByteWindowI window, byte[] content) {
		SerializerI ser = window.getSerializer();
		if (ser == null) {
			return this.getAsHex(content);
		}
		Object obj = ser.deserialize(content);

		return obj == null ? "<null>" : String.valueOf(obj);

	}

	public void format(ByteCursorI cur, Writer writer) {
		try {
			this.formatInternal(cur, writer);
		} catch (IOException e) {
			throw GsException.toRuntimeException(e);

		}
	}

	protected abstract void formatInternal(ByteCursorI cur, Writer writer) throws IOException;
}
