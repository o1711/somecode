package com.graphscape.commons.file.support;

import java.io.Writer;

import com.graphscape.commons.file.PlainRegionI;
import com.graphscape.commons.lang.GsException;

public class ProxyPlainRegion2 extends ProxyRegion<PlainRegionI> implements PlainRegionI {

	protected long offset;

	protected long length;

	public ProxyPlainRegion2(PlainRegionI t, long offset, long len) {
		super(t);
		this.offset = offset;
		if (this.offset < 0) {
			throw new IllegalArgumentException("negative offset:" + offset);
		}
		this.length = len;
		if (this.length < 0) {
			throw new IllegalArgumentException("negative length:" + length);
		}
	}

	protected void assertBount(long pointer, int len) {
		if (pointer < 0 || pointer + len > this.length) {
			throw new GsException("out of bound,pointer:" + pointer + ",len:" + len + ",length:"
					+ this.length);
		}
		
	}

	@Override
	public void read(long pointer, byte[] buffer) {
		this.assertBount(pointer, buffer.length);
		this.target.read(pointer + offset, buffer);
	}

	@Override
	public void read(long pointer, byte[] buffer, int off, int len) {
		this.assertBount(pointer, len);
		
		this.target.read(pointer + offset, buffer, off, len);
	}

	@Override
	public int readInt(long pointer) {
		this.assertBount(pointer, 4);
		
		return this.target.readInt(pointer + offset);
	}

	@Override
	public long readLong(long pointer) {
		this.assertBount(pointer, 8);
		
		return this.target.readLong(pointer + offset);
	}

	@Override
	public short readShort(long pointer) {
		this.assertBount(pointer, 2);
		
		return this.target.readShort(pointer + offset);
	}

	@Override
	public byte readByte(long pointer) {
		this.assertBount(pointer, 1);
		
		return this.target.readByte(pointer + offset);
	}

	@Override
	public byte[] read(long pointer, int len) {
		this.assertBount(pointer, len);
		
		return this.target.read(pointer + offset, len);

	}

	@Override
	public void write(long pointer, byte[] buffer) {
		this.assertBount(pointer, buffer.length);
		
		this.target.write(pointer + offset, buffer);

	}

	@Override
	public void write(long pointer, byte[] buffer, int off, int len) {
		this.assertBount(pointer, len);
		this.target.write(pointer + offset, buffer, off, len);
	}

	@Override
	public void writeLong(long pointer, long data) {
		this.assertBount(pointer, 8);
		this.target.writeLong(pointer + offset, data);
	}

	@Override
	public void writeInt(long pointer, int data) {
		this.assertBount(pointer, 4);
		this.target.writeInt(pointer + offset, data);
	}

	@Override
	public void writeShort(long pointer, short data) {
		this.assertBount(pointer, 2);
		this.target.writeShort(pointer + offset, data);
	}

	@Override
	public void writeByte(long pointer, byte data) {
		this.assertBount(pointer, 1);
		this.target.writeByte(pointer + offset, data);
	}

	@Override
	public long getLength() {
		return this.length;
	}

	@Override
	public void dump(Writer writer) {
		this.target.dump(writer);
	}

	@Override
	public void dump() {
		this.target.dump();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.file.PlainRegionI#subRegion(long, int)
	 */
	@Override
	public PlainRegionI subRegion(long offset, long len) {
		return this.target.subRegion(this.offset + offset, len);
	}

	@Override
	public PlainRegionI subRegion(long offset) {		
		return this.target.subRegion(offset + this.offset,this.length - offset);
	}

}
