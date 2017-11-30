/**
 * Jan 12, 2014
 */
package com.graphscape.commons.file.provider;

import java.io.IOException;
import java.io.Writer;

import com.graphscape.commons.file.PlainRegionI;
import com.graphscape.commons.file.support.DumpableSupport;
import com.graphscape.commons.file.support.ProxyPlainRegion;
import com.graphscape.commons.file.support.ProxyPlainRegion2;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.util.ByteArrayUtil;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class MemoryPlainRegion extends DumpableSupport implements PlainRegionI {

	boolean lengthFixed;

	int length;

	private static int step = 128;

	byte[] content = new byte[step];

	int startOffset;

	public MemoryPlainRegion() {
		this(0, false);
	}

	public MemoryPlainRegion(int offset) {
		this(offset, true);
	}

	public MemoryPlainRegion(int offset, boolean fixLenth) {
		this.content = new byte[step];//
		this.startOffset = offset;
		this.lengthFixed = fixLenth;
	}

	public MemoryPlainRegion(byte[] content) {
		this(content, 0, content.length, true);//
	}

	public MemoryPlainRegion(byte[] content, int offset, int length, boolean fixLength) {
		this.content = content;
		this.startOffset = offset;
		this.lengthFixed = fixLength;
		this.length = length;
	}

	public static PlainRegionI valueOf(byte[] content) {
		return new MemoryPlainRegion(content, 0, content.length, true);
	}

	@Override
	public long getLength() {
		return this.length;
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
	public void read(long pointer, byte[] buffer, int off, int len) {

		pointer = pointerForRead(pointer, len);
		System.arraycopy(this.content, (int) pointer, buffer, off, len);
	}

	private long pointerForWrite(long pointer, int len) {
		long rt = this.startOffset + pointer;
		return rt;
	}

	private long pointerForRead(long pointer, int len) {
		long rt = this.pointerForWrite(pointer, len);
		if (rt + len > this.length) {
			throw new GsException("EOF,max length:" + length + ",pointer:" + pointer + ",start offset:"
					+ this.startOffset + ",len:" + len);
		}

		return rt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.file.PlainRegionI#write(long, byte[])
	 */
	@Override
	public void write(long pointer, byte[] buffer) {
		write(pointer, buffer, 0, buffer.length);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.file.PlainRegionI#write(long, byte[], int,
	 * int)
	 */
	@Override
	public void write(long pointer, byte[] buffer, int off, int len) {

		int len2 = (int) pointer + len;

		this.assertSpace(len2);

		if (this.length < len2) {
			this.length = len2;
		}

		System.arraycopy(buffer, off, this.content, (int) pointer, len);

	}

	private void assertSpace(int length) {
		int expand = this.content.length - length;

		if (expand <= 0) {
			return;
		}
		if (this.lengthFixed) {
			throw new GsException("cannot expand, it is fixed");
		}

		expand = (expand % step + 1) * step;

		byte[] content2 = new byte[this.content.length + expand];

		System.arraycopy(this.content, 0, content2, 0, this.content.length);

		this.content = content2;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.file.PlainRegionI#writeLong(long, long)
	 */
	@Override
	public void writeLong(long pointer, long data) {
		write(pointer, ByteArrayUtil.writeLong(data));//
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.file.PlainRegionI#writeInt(long, int)
	 */
	@Override
	public void writeInt(long pointer, int data) {
		write(pointer, ByteArrayUtil.writeInt(data));//

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.file.PlainRegionI#writeShort(long, short)
	 */
	@Override
	public void writeShort(long pointer, short data) {
		write(pointer, ByteArrayUtil.writeShort(data));//

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.file.PlainRegionI#writeByte(long, byte)
	 */
	@Override
	public void writeByte(long pointer, byte data) {
		this.write(pointer, new byte[] { data }, 0, 1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.file.PlainRegionI#readInt(long)
	 */
	@Override
	public int readInt(long pointer) {
		pointer = pointerForRead(pointer, 4);
		return ByteArrayUtil.readInt((int) pointer, this.content);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.file.PlainRegionI#readLong(long)
	 */
	@Override
	public long readLong(long pointer) {
		pointer = pointerForRead(pointer, 8);
		return ByteArrayUtil.readLong((int) pointer, this.content);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.file.PlainRegionI#readShort(long)
	 */
	@Override
	public short readShort(long pointer) {
		pointer = pointerForRead(pointer, 2);
		return ByteArrayUtil.readShort((int) pointer, this.content);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.file.PlainRegionI#readByte(long)
	 */
	@Override
	public byte readByte(long pointer) {
		pointer = pointerForRead(pointer, 1);
		return this.content[(int) pointer];

	}

	@Override
	public void dumpInternal(Writer writer) {
		long len = this.getLength();
		byte[] buf = new byte[(int) len];// TODO
		this.read(0, buf);
		try {
			writer.write(ByteArrayUtil.hex(buf));
		} catch (IOException e) {
			throw new GsException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.file.PlainRegionI#subRegion(long, long)
	 */
	@Override
	public PlainRegionI subRegion(long offset, long len) {
		return new ProxyPlainRegion2(this, offset, len);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.file.PlainRegionI#subRegion(long)
	 */
	@Override
	public PlainRegionI subRegion(long offset) {
		return new ProxyPlainRegion(this, offset);
	}

	@Override
	public long getCapacity() {
		return this.content.length;
	}

}
