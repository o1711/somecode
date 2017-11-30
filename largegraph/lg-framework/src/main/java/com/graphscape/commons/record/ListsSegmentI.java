package com.graphscape.commons.record;

public interface ListsSegmentI extends SegmentI {

	public long addList(byte[][] content);
	
	public byte[] getElement(long pointer);
	
	public long addElement(long pointer, byte[] content);
	
	public long updateElement(long pointer, byte[] content);

	public void removeList(long pointer);

}
