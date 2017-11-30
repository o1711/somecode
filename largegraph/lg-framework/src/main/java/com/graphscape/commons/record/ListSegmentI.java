package com.graphscape.commons.record;

public interface ListSegmentI extends SegmentI {

	public long update(long pointer, byte[] content);

	public long add(byte[] content);

	public byte[] get(long pointer);

	public void remove(long pointer);

	public void clear();
	public long insert(long prePointer, byte[] content);

	public long getFirstPointer();

	public long getNextPointer(long pointer);

	public long getPrePointer(long pointer);

	public long getLasterPointer();

	public boolean removeByContent(byte[] content);

	public long[] getPointerList();

	public byte[][] getContentList();

}
