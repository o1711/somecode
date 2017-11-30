package com.graphscape.commons.file.support;

import com.graphscape.commons.debug.support.TracableSupport;
import com.graphscape.commons.file.ByteReadableI;
import com.graphscape.commons.file.ByteWritableI;

public class ProxyReaderWriter extends TracableSupport implements ByteWritableI, ByteReadableI {

	protected ByteWritableI targetWriter;

	protected ByteReadableI targetReader;

	public ProxyReaderWriter(ByteReadableI t, ByteWritableI target) {
		this.targetReader = t;
		this.targetWriter = target;
	}

	@Override
	public void write(long pointer, byte[] buffer) {
		this.targetWriter.write(pointer, buffer);

	}

	@Override
	public void write(long pointer, byte[] buffer, int off, int len) {
		this.targetWriter.write(pointer, buffer, off, len);
	}

	@Override
	public void writeLong(long pointer, long data) {
		this.targetWriter.writeLong(pointer, data);
	}

	@Override
	public void writeInt(long pointer, int data) {
		this.targetWriter.writeInt(pointer, data);
	}

	@Override
	public void writeShort(long pointer, short data) {
		this.targetWriter.writeShort(pointer, data);
	}

	@Override
	public void writeByte(long pointer, byte data) {
		this.targetWriter.writeByte(pointer, data);
	}

	@Override
	public void read(long pointer, byte[] buffer) {
		this.targetReader.read(pointer, buffer);
	}

	@Override
	public void read(long pointer, byte[] buffer, int off, int len) {
		this.targetReader.read(pointer, buffer, off, len);
	}

	@Override
	public int readInt(long pointer) {
		// TODO Auto-generated method stub
		return this.targetReader.readInt(pointer);
	}

	@Override
	public long readLong(long pointer) {
		// TODO Auto-generated method stub
		return this.targetReader.readLong(pointer);
	}

	@Override
	public short readShort(long pointer) {
		// TODO Auto-generated method stub
		return this.targetReader.readShort(pointer);
	}

	@Override
	public byte readByte(long pointer) {
		// TODO Auto-generated method stub
		return this.targetReader.readByte(pointer);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.file.PlainRegionI#read(long, int)
	 */
	@Override
	public byte[] read(long pointer, int length) {
		// TODO Auto-generated method stub
		return this.targetReader.read(pointer, length);

	}
}
