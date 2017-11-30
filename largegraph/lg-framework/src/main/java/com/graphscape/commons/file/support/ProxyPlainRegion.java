package com.graphscape.commons.file.support;

import java.io.Writer;

import com.graphscape.commons.file.PlainRegionI;

public class ProxyPlainRegion extends ProxyRegion<PlainRegionI> implements PlainRegionI {

	protected long offset;
	
	public ProxyPlainRegion(PlainRegionI t) {
		this(t, 0);
	}
	
	public ProxyPlainRegion(PlainRegionI t, long offset) {
		super(t);
		this.offset = offset;
		if (this.offset < 0) {
			throw new IllegalArgumentException("negative offset:" + offset);
		}
	}

	@Override
	public void read(long pointer, byte[] buffer) {
		this.target.read(pointer + offset, buffer);
	}

	@Override
	public void read(long pointer, byte[] buffer, int off, int len) {
		this.target.read(pointer + offset, buffer, off, len);
	}

	@Override
	public int readInt(long pointer) {
		// TODO Auto-generated method stub
		return this.target.readInt(pointer + offset);
	}

	@Override
	public long readLong(long pointer) {
		// TODO Auto-generated method stub
		return this.target.readLong(pointer + offset);
	}

	@Override
	public short readShort(long pointer) {
		// TODO Auto-generated method stub
		return this.target.readShort(pointer + offset);
	}

	@Override
	public byte readByte(long pointer) {
		// TODO Auto-generated method stub
		return this.target.readByte(pointer + offset);
	}

	@Override
	public byte[] read(long pointer, int length) {
		// TODO Auto-generated method stub
		return this.target.read(pointer + offset, length);

	}

	@Override
	public void write(long pointer, byte[] buffer) {
		this.target.write(pointer + offset, buffer);

	}

	@Override
	public void write(long pointer, byte[] buffer, int off, int len) {
		this.target.write(pointer + offset, buffer, off, len);
	}

	@Override
	public void writeLong(long pointer, long data) {
		this.target.writeLong(pointer + offset, data);
	}

	@Override
	public void writeInt(long pointer, int data) {
		this.target.writeInt(pointer + offset, data);
	}

	@Override
	public void writeShort(long pointer, short data) {
		this.target.writeShort(pointer + offset, data);
	}

	@Override
	public void writeByte(long pointer, byte data) {
		this.target.writeByte(pointer + offset, data);
	}

	@Override
	public long getLength() {
		long len = super.getLength();

		return len - offset;
	}

	@Override
	public void dump(Writer writer) {
		this.target.dump(writer);
	}

	@Override
	public void dump() {
		this.target.dump();
	}

	/* (non-Javadoc)
	 * @see com.graphscape.commons.file.PlainRegionI#subRegion(long, int)
	 */
	@Override
	public PlainRegionI subRegion(long offset, long len) {
		return this.target.subRegion(this.offset+offset, len);
	}

	/* (non-Javadoc)
	 * @see com.graphscape.commons.file.PlainRegionI#subRegion(long)
	 */
	@Override
	public PlainRegionI subRegion(long offset) {
		long len = this.target.getLength()-offset;
		
		return this.target.subRegion(this.offset+offset, len);
	}
	

}
