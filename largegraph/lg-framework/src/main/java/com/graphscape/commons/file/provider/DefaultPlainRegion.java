/**
 * Jan 4, 2014
 */
package com.graphscape.commons.file.provider;

import java.io.IOException;
import java.io.Writer;

import org.apache.commons.vfs2.RandomAccessContent;

import com.graphscape.commons.file.PlainRegionI;
import com.graphscape.commons.file.support.DumpableSupport;
import com.graphscape.commons.file.support.ProxyPlainRegion2;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.util.ByteArrayUtil;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class DefaultPlainRegion extends DumpableSupport implements PlainRegionI {

	private static final String PROFILE_WRITE = DefaultPlainRegion.class.getName() + ".write";

	private static final String PROFILE_READ = DefaultPlainRegion.class.getName() + ".read";

	private static final String PROFILE_DOREAD = DefaultPlainRegion.class.getName() + ".doRead";

	private static final String PROFILE_BEFORE_WRITE = DefaultPlainRegion.class.getName() + ".beforeWrite";

	private static final String PROFILE_AFTER_WRITE = DefaultPlainRegion.class.getName() + ".afterWrite";

	private static final String PROFILE_SEEK = DefaultPlainRegion.class.getName() + ".seek";

	private static final String PROFILE_WRITE2 = DefaultPlainRegion.class.getName() + ".doWrite";

	private static final String PROFILE_EXPAND = DefaultPlainRegion.class.getName() + ".expand";

	private String name;

	private RandomAccessContent content;

	private long offset;

	private long length;

	private long capacity;

	private int expand;

	public DefaultPlainRegion(String name, RandomAccessContent content, long offset, long length,
			long capacity, int expand) {

		this.name = name;
		this.content = content;
		this.offset = offset;
		this.length = length;
		this.capacity = capacity;
		this.expand = expand;
	}

	@Override
	public void read(long pos, byte[] buf, int off, int len) {
		this.beforeProfile(PROFILE_READ);
		try {
			beforeRead(pos, len);
			this.doRead(pos, buf, off, len);
		} catch (IOException e) {
			throw new GsException(e);
		} finally {
			this.afterProfile();
		}
	}

	private void doRead(long pos, byte[] buf, int off, int len) throws IOException {
		this.beforeProfile(PROFILE_SEEK);
		try {
			this.content.seek(offset + pos);
		} finally {
			this.afterProfile();
		}
		this.beforeProfile(PROFILE_DOREAD);
		try {
			this.content.readFully(buf, off, len);
		} finally {
			this.afterProfile();
		}
	}

	@Override
	public void write(long pos, byte[] buf, int off, int len) {
		this.beforeProfile(PROFILE_WRITE);
		try {

			beforeWrite(pos, len);
			this.doWrite(pos, buf, off, len);
			this.afterWrite(pos, len);

		} catch (IOException e) {
			throw new GsException(e);
		} finally {
			this.afterProfile();
		}
	}

	private void doWrite(long pos, byte[] buf, int off, int len) throws IOException {

		this.beforeProfile(PROFILE_SEEK);
		try {
			this.content.seek(offset + pos);
		} finally {
			this.afterProfile();
		}
		this.beforeProfile(PROFILE_WRITE2);
		try {
			this.content.write(buf, off, len);
		} finally {
			this.afterProfile();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.record.FileI#writeLong(long, long)
	 */
	@Override
	public void writeLong(long pos, long data) {
		this.write(pos, ByteArrayUtil.writeLong(data));//
	}

	@Override
	public void writeInt(long pos, int data) {
		this.write(pos, ByteArrayUtil.writeInt(data));//
	}

	@Override
	public void writeShort(long pos, short data) {
		this.write(pos, ByteArrayUtil.writeShort(data));//
	}

	@Override
	public void writeByte(long pos, byte data) {
		this.write(pos, new byte[] { data });//
	}

	@Override
	public void write(long pos, byte[] buf) {
		this.write(pos, buf, 0, buf.length);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.record.FileI#read(long, byte[])
	 */
	@Override
	public void read(long pos, byte[] buf) {
		this.read(pos, buf, 0, buf.length);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.record.FileI#readInt(long)
	 */
	@Override
	public int readInt(long pos) {
		byte[] buf = new byte[4];
		this.read(pos, buf);
		return ByteArrayUtil.readInt(buf);//
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.record.FileI#readLong(long)
	 */
	@Override
	public long readLong(long pos) {
		byte[] buf = new byte[8];
		this.read(pos, buf);
		return ByteArrayUtil.readLong(buf);//
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.record.FileI#readShort(long)
	 */
	@Override
	public short readShort(long pos) {
		byte[] buf = new byte[2];
		this.read(pos, buf);
		return ByteArrayUtil.readShort(buf);//
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.record.FileI#readByte(long)
	 */
	@Override
	public byte readByte(long pos) {
		byte[] buf = new byte[1];
		this.read(pos, buf);
		return buf[0];//
	}

	private void beforeRead(long pos, int len) {
		if (pos < 0 || pos + len > this.length) {
			throw new GsException("out of read boundary,pos:" + pos + ",len:" + len + ",length:"
					+ this.length + ",region:" + this.name);
		}
	}

	private void beforeWrite(long pos, int len) {
		this.beforeProfile(PROFILE_BEFORE_WRITE);
		try {
			this.doBeforeWrite(pos, len);
		} finally {
			this.afterProfile();
		}
	}

	private void doBeforeWrite(long pos, int len) {

		if (pos < 0) {
			throw new GsException("out of write boundary,pos:" + pos + ",len:" + len);
		}
		if (len == 0) {
			// throw new GsException("len is zero?");
		}

		long newLength = pos + len;

		if (newLength > this.capacity) {//
			//
			this.makeSureCapacity(newLength);
		}
	}

	private void afterWrite(long pos, int len) {
		this.beforeProfile(PROFILE_AFTER_WRITE);
		try {

			long newLength = pos + len;
			if (newLength > this.length) {
				this.setLength(pos + len);
			}
		} finally {
			this.afterProfile();
		}
	}

	private void makeSureCapacity(long required) {

		if (this.expand == 0) {// cannot expand.
			throw new GsException();
		}

		if (this.capacity >= required) {
			return;
		}

		this.capacity = (required / this.expand + 1) * this.expand;

		try {

			long currentLength = this.content.length();

			// its safe to write really after the length of the file.
			// otherwise it may override the current content.
			if (this.capacity > currentLength) {
				this.doWrite(this.capacity - 1, new byte[] { 0 }, 0, 1);
			} else {// TODO shink?

			}

		} catch (IOException e) {
			throw new GsException(e);
		}

	}

	@Override
	public long getLength() {

		return this.length;
	}

	/**
	 * update length;
	 */
	public void setLength(long len) {
		this.length = len;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.file.PlainRegionI#read(long, int)
	 */
	@Override
	public byte[] read(long pointer, int length) {
		try {

			byte[] rt = new byte[length];
			read(pointer, rt);
			return rt;
		} catch (OutOfMemoryError e) {
			throw e;
		}

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
	 * @see com.graphscape.commons.file.PlainRegionI#subRegion(long, int)
	 */
	@Override
	public PlainRegionI subRegion(long offset, long len) {

		return new ProxyPlainRegion2(this, offset, len);
	}

	@Override
	public PlainRegionI subRegion(long offset) {
		long len = this.getLength() - offset;
		return this.subRegion(offset, len);
	}

	@Override
	public long getCapacity() {
		return this.capacity;
	}

}
