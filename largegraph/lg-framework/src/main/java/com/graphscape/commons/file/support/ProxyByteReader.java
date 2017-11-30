package com.graphscape.commons.file.support;

import com.graphscape.commons.file.ByteReadableI;
import com.graphscape.commons.lang.Wrapper;

public class ProxyByteReader extends Wrapper<ByteReadableI> implements ByteReadableI {

	public ProxyByteReader(ByteReadableI t) {
		super(t);
	}

	@Override
	public void read(long pointer, byte[] buffer) {
		this.target.read(pointer, buffer);
	}

	@Override
	public void read(long pointer, byte[] buffer, int off, int len) {
		this.target.read(pointer, buffer, off, len);
	}

	@Override
	public int readInt(long pointer) {
		// TODO Auto-generated method stub
		return this.target.readInt(pointer);
	}

	@Override
	public long readLong(long pointer) {
		// TODO Auto-generated method stub
		return this.target.readLong(pointer);
	}

	@Override
	public short readShort(long pointer) {
		// TODO Auto-generated method stub
		return this.target.readShort(pointer);
	}

	@Override
	public byte readByte(long pointer) {
		// TODO Auto-generated method stub
		return this.target.readByte(pointer);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.file.PlainRegionI#read(long, int)
	 */
	@Override
	public byte[] read(long pointer, int length) {
		// TODO Auto-generated method stub
		return this.target.read(pointer, length);

	}

}
