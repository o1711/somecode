package com.graphscape.commons.file.provider;

import java.util.ArrayList;
import java.util.List;

import com.graphscape.commons.file.ByteCursorI;
import com.graphscape.commons.file.ByteReadableI;
import com.graphscape.commons.file.support.ByteReaderSupport;
import com.graphscape.commons.lang.GsException;

public class DefaultCursorReader extends ByteReaderSupport {
	ByteReadableI target;
	ByteCursorI cursor;

	public DefaultCursorReader(ByteCursorI cursor, ByteReadableI target) {
		super(false);
		this.target = target;
		this.cursor = cursor;
	}

	@Override
	public void read(long pointer, byte[] buffer, int off, int len) {
		this.beforeExecute("read", this.cursor.getPointer(), pointer, buffer, off, len);
		try {
			this.doRead(pointer, buffer, off, len);
		} catch (GsException e) {
			this.onException(e);
			throw e;
		} finally {
			this.afterExecute();
		}
	}

	public void doRead(long pointer, byte[] buffer, int off, int len) {
		List<Object> call = new ArrayList<Object>();

		long aoff = this.cursor.getAbsoluteOffset();

		long windownLen = cursor.getLength();

		if (pointer + len > windownLen) {
			
			throw new GsException("overflow,pointer:" + pointer + ",len:" + len + ",window length:"
					+ windownLen);
		}

		this.target.read(pointer + aoff, buffer, off, len);

	}

}
