package com.graphscape.commons.file;

public interface ByteReadableI {

	//public byte[] read();
	
	public byte[] read(long pointer, int length);

	public void read(long pointer, byte[] buffer);

	public void read(long pointer, byte[] buffer, int off, int len);

	public int readInt(long pointer);

	public long readLong(long pointer);

	public short readShort(long pointer);

	public byte readByte(long pointer);
}
