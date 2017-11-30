/**
 * Jan 5, 2014
 */
package com.graphscape.commons.file;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public interface RowRegionI extends RegionI {

	public long getRows();
	
	public void setRowWidth(int rowWidth);
	
	public int getRowWidth();
	
	public byte[] readRow(long row);

	public void read(long row, int offset, byte[] buffer);

	public void read(long row, int offset, byte[] buffer, int off, int len);

	public void write(long row, int offset, byte[] buffer);

	public void write(long row, int offset, byte[] buffer, int off, int len);

	public void writeLong(long row, int offset, long data);

	public void writeInt(long row, int offset, int data);

	public void writeShort(long row, int offset, short data);

	public void writeByte(long row, int offset, byte data);

	public int readInt(long row, int offset);

	public long readLong(long row, int offset);

	public short readShort(long row, int offset);

	public byte readByte(long row, int offset);

}
