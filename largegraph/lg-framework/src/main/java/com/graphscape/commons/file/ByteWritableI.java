package com.graphscape.commons.file;

public interface ByteWritableI {

	public void write(long pointer, byte[] buffer);

	public void write(long pointer, byte[] buffer, int off, int len);

	public void writeLong(long pointer, long data);

	public void writeInt(long pointer, int data);

	public void writeShort(long pointer, short data);

	public void writeByte(long pointer, byte data);

}
