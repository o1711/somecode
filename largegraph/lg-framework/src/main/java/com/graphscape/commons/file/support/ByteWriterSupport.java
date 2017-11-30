/**
 * Jan 12, 2014
 */
package com.graphscape.commons.file.support;

import com.graphscape.commons.file.ByteWritableI;
import com.graphscape.commons.util.ByteArrayUtil;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public abstract class ByteWriterSupport implements ByteWritableI {

	boolean fixLength;

	int length = 0;

	public ByteWriterSupport() {
		this(false);
	}

	public ByteWriterSupport(boolean fixLenth) {
		this.fixLength = fixLenth;
	}

	@Override
	public void write(long pointer, byte[] buffer) {
		write(pointer, buffer, 0, buffer.length);
	}

	public abstract void write(long pointer, byte[] buffer, int off, int len);

	@Override
	public void writeLong(long pointer, long data) {
		write(pointer, ByteArrayUtil.writeLong(data));//
	}

	@Override
	public void writeInt(long pointer, int data) {
		write(pointer, ByteArrayUtil.writeInt(data));//

	}

	@Override
	public void writeShort(long pointer, short data) {
		write(pointer, ByteArrayUtil.writeShort(data));//
	}

	@Override
	public void writeByte(long pointer, byte data) {
		this.write(pointer, new byte[] { data });//
	}

}
