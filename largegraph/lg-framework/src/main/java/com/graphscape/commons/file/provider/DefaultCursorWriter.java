package com.graphscape.commons.file.provider;

import java.util.ArrayList;
import java.util.List;

import com.graphscape.commons.file.ByteWritableI;
import com.graphscape.commons.file.support.ByteWriterSupport;
import com.graphscape.commons.lang.GsException;

public class DefaultCursorWriter extends ByteWriterSupport {
	DefaultByteCursor cursor;
	ByteWritableI target;

	public DefaultCursorWriter(DefaultByteCursor cursor, ByteWritableI target) {
		this.cursor = cursor;
		this.target = target;
	}

	@Override
	public void write(long pointer, byte[] buffer, int off, int len) {
		List<Object> call = new ArrayList<Object>();

		long aoff = this.cursor.getAbsoluteOffset();
		long windownLen = cursor.getLength();
		if (pointer + len > windownLen) {
			throw new GsException("overflow,pointer:" + pointer + ",len:" + len + ",window length:"
					+ windownLen + ",window:" + this.cursor.getPointer());
		}

		this.target.write(pointer + aoff, buffer, off, len);
	}

}
