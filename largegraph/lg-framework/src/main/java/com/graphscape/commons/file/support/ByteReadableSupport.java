package com.graphscape.commons.file.support;

import com.graphscape.commons.file.ByteReadableI;
import com.graphscape.commons.util.ByteArrayUtil;

public abstract class ByteReadableSupport extends DumpableSupport implements ByteReadableI {

	@Override
	public byte[] read(long pointer, int length) {
		byte[] buffer = new byte[length];
		this.read(pointer, buffer, 0, length);
		return buffer;

	}

	@Override
	public void read(long pointer, byte[] buffer) {

		this.read(pointer, buffer, 0, buffer.length);

	}

	@Override
	public abstract void read(long pointer, byte[] buffer, int off, int len);

	@Override
	public int readInt(long pointer) {
		byte[] buffer = new byte[4];
		this.read(pointer, buffer);
		return ByteArrayUtil.readInt(buffer);

	}

	@Override
	public long readLong(long pointer) {
		byte[] buffer = new byte[8];
		this.read(pointer, buffer);
		return ByteArrayUtil.readLong(buffer);

	}

	@Override
	public short readShort(long pointer) {
		byte[] buffer = new byte[2];
		this.read(pointer, buffer);
		return ByteArrayUtil.readShort(buffer);

	}

	@Override
	public byte readByte(long pointer) {
		byte[] buffer = new byte[1];
		this.read(pointer, buffer);
		return buffer[0];//
	}

}
