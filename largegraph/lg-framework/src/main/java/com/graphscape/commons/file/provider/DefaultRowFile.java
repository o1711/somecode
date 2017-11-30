/**
 * Jan 5, 2014
 */
package com.graphscape.commons.file.provider;

import com.graphscape.commons.file.PlainRegionI;
import com.graphscape.commons.file.RowRegionI;
import com.graphscape.commons.file.support.ProxyPlainRegion;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class DefaultRowFile extends ProxyPlainRegion implements RowRegionI {

	private int rowWidth;

	public DefaultRowFile(PlainRegionI pf) {
		super(pf);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.file.RowFileI#getRowWidth()
	 */
	@Override
	public int getRowWidth() {
		// TODO Auto-generated method stub
		return this.rowWidth;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.file.RowFileI#read(long, int, byte[])
	 */
	@Override
	public void read(long row, int offset, byte[] buffer) {
		this.target.read(row * this.rowWidth + offset, buffer);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.file.RowFileI#read(long, int, byte[], int,
	 * int)
	 */
	@Override
	public void read(long row, int offset, byte[] buffer, int off, int len) {
		this.target.read(row * this.rowWidth + offset, buffer, off, len);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.file.RowFileI#write(long, int, byte[])
	 */
	@Override
	public void write(long row, int offset, byte[] buffer) {
		this.target.write(row * this.rowWidth + offset, buffer);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.file.RowFileI#write(long, int, byte[], int,
	 * int)
	 */
	@Override
	public void write(long row, int offset, byte[] buffer, int off, int len) {
		this.target.write(row * this.rowWidth + offset, buffer, off, len);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.file.RowFileI#writeLong(long, int, long)
	 */
	@Override
	public void writeLong(long row, int offset, long data) {
		this.target.writeLong(row * this.rowWidth + offset, data);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.file.RowFileI#writeInt(long, int, int)
	 */
	@Override
	public void writeInt(long row, int offset, int data) {
		this.target.writeInt(row * this.rowWidth + offset, data);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.file.RowFileI#writeShort(long, int, short)
	 */
	@Override
	public void writeShort(long row, int offset, short data) {
		this.target.writeShort(row * this.rowWidth + offset, data);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.file.RowFileI#writeByte(long, int, byte)
	 */
	@Override
	public void writeByte(long row, int offset, byte data) {
		this.target.writeByte(row * this.rowWidth + offset, data);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.file.RowFileI#readInt(long, int)
	 */
	@Override
	public int readInt(long row, int offset) {

		return this.target.readInt(row * this.rowWidth + offset);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.file.RowFileI#readLong(long, int)
	 */
	@Override
	public long readLong(long row, int offset) {
		// TODO Auto-generated method stub
		return this.target.readLong(row * this.rowWidth + offset);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.file.RowFileI#readShort(long, int)
	 */
	@Override
	public short readShort(long row, int offset) {
		// TODO Auto-generated method stub
		return this.target.readShort(row * this.rowWidth + offset);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.file.RowFileI#readByte(long, int)
	 */
	@Override
	public byte readByte(long row, int offset) {
		// TODO Auto-generated method stub
		return this.target.readByte(row * this.rowWidth + offset);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.file.RowFileI#getRows()
	 */
	@Override
	public long getRows() {
		return this.target.getLength() / this.rowWidth;
	}

	@Override
	public void setRowWidth(int rowWidth) {
		this.rowWidth = rowWidth;
	}

	/* (non-Javadoc)
	 * @see com.graphscape.commons.file.RowRegionI#readRow(long)
	 */
	@Override
	public byte[] readRow(long row) {
		// TODO Auto-generated method stub
		byte[] buffer = new byte[this.rowWidth];
		this.target.read(row * this.rowWidth, buffer);
		return buffer;
	}

}
