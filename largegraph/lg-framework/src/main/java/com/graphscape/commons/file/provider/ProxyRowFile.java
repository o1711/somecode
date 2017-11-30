/**
 * Jan 4, 2014
 */
package com.graphscape.commons.file.provider;

import com.graphscape.commons.file.RowRegionI;
import com.graphscape.commons.lang.Wrapper;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class ProxyRowFile extends Wrapper<RowRegionI> implements RowRegionI {

	/**
	 * @param t
	 */
	public ProxyRowFile() {
		super(null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.record.FileI#read(long, byte[], int, int)
	 */
	@Override
	public void read(long row, int offset, byte[] buffer, int off, int len) {
		this.target.read(row, offset, buffer, off, len);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.record.FileI#write(long, byte[], int, int)
	 */
	@Override
	public void write(long row, int offset, byte[] buffer, int off, int len) {
		this.target.write(row, offset, buffer, off, len);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.record.FileI#writeLong(long, long)
	 */
	@Override
	public void writeLong(long row, int offset, long data) {
		this.target.writeLong(row, offset, data);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.record.FileI#writeInt(long, int)
	 */
	@Override
	public void writeInt(long row, int offset, int data) {
		this.target.writeInt(row, offset, data);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.record.FileI#writeShort(long, short)
	 */
	@Override
	public void writeShort(long row, int offset, short data) {
		this.target.writeShort(row, offset, data);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.record.FileI#writeByte(long, byte)
	 */
	@Override
	public void writeByte(long row, int offset, byte data) {
		this.target.writeByte(row, offset, data);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.record.FileI#read(long, byte[])
	 */
	@Override
	public void read(long row, int offset, byte[] buffer) {
		this.target.read(row, offset, buffer);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.record.FileI#write(long, byte[])
	 */
	@Override
	public void write(long row, int offset, byte[] buffer) {
		this.target.write(row, offset, buffer);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.record.FileI#readInt(long)
	 */
	@Override
	public int readInt(long row, int offset) {
		// TODO Auto-generated method stub
		return this.target.readInt(row, offset);

	}

	@Override
	public long readLong(long row, int offset) {
		// TODO Auto-generated method stub
		return this.target.readLong(row, offset);
	}

	@Override
	public short readShort(long row, int offset) {
		return this.target.readShort(row, offset);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.record.FileI#readByte(long)
	 */
	@Override
	public byte readByte(long row, int offset) {
		return this.target.readByte(row, offset);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.file.RowFileI#getRowWidth()
	 */
	@Override
	public int getRowWidth() {
		// TODO Auto-generated method stub
		return this.target.getRowWidth();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.file.RowFileI#getRows()
	 */
	@Override
	public long getRows() {
		// TODO Auto-generated method stub
		return this.target.getRows();
	}

	@Override
	public long getLength() {
		
		return this.target.getLength();
	}

	@Override
	public void setRowWidth(int rowWidth) {
		this.target.setRowWidth(rowWidth);
	}

	/* (non-Javadoc)
	 * @see com.graphscape.commons.file.RowRegionI#readRow(long)
	 */
	@Override
	public byte[] readRow(long row) {
		// TODO Auto-generated method stub
		return this.target.readRow(row);//
	}

	@Override
	public long getCapacity() {
		
		return this.target.getCapacity();
	}

}
