/**
 * Jan 12, 2014
 */
package com.graphscape.commons.file.support;

import com.graphscape.commons.debug.support.TracableSupport;
import com.graphscape.commons.file.ByteReadableI;
import com.graphscape.commons.util.ByteArrayUtil;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public abstract class ByteReaderSupport extends TracableSupport implements ByteReadableI {

	boolean fixLength;

	public ByteReaderSupport(boolean fixLength) {
		this.fixLength = fixLength;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.file.PlainRegionI#read(long, int)
	 */
	@Override
	public byte[] read(long pointer, int length) {
		byte[] rt = new byte[length];
		read(pointer, rt);
		return rt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.file.PlainRegionI#read(long, byte[])
	 */
	@Override
	public void read(long pointer, byte[] buffer) {
		read(pointer, buffer, 0, buffer.length);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.file.PlainRegionI#read(long, byte[], int,
	 * int)
	 */
	@Override
	public abstract void read(long pointer, byte[] buffer, int off, int len);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.file.PlainRegionI#readInt(long)
	 */
	@Override
	public int readInt(long pointer) {
		byte[] buf = this.read(pointer, 4);
		return ByteArrayUtil.readInt(buf);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.file.PlainRegionI#readLong(long)
	 */
	@Override
	public long readLong(long pointer) {
		byte[] buf = this.read(pointer, 8);
		return ByteArrayUtil.readLong(buf);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.file.PlainRegionI#readShort(long)
	 */
	@Override
	public short readShort(long pointer) {
		byte[] buf = this.read(pointer, 2);
		return ByteArrayUtil.readShort(buf);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.file.PlainRegionI#readByte(long)
	 */
	@Override
	public byte readByte(long pointer) {
		byte[] buf = this.read(pointer, 1);
		return buf[0];//
	}

}
