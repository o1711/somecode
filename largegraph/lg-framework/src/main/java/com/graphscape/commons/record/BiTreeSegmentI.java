package com.graphscape.commons.record;

public interface BiTreeSegmentI extends SegmentI {

	public long update(long pointer, byte[] content);

	public byte[] get(long pointer);

	public void remove(long pointer);

	public void clear();
	
	public long setRootPointer(long pointer);
	
	public long getRootPointer();

	public long getParentPointer(long pointer);
	
	public long addRoot(byte[] header,byte[] content);

	public long addLeft(long parentPointer, byte[] header, byte[] content);

	public long addRight(long parentPointer, byte[] header, byte[] content);
	
	public long updateUserHeader(long pointer,byte[] header);
	
	public byte[] getUserHeader(long pointer);

	public long getLeftPointer(long parentPointer);

	public long getRightPointer(long parentPointer);

	// return the old left pointer
	public long removeLeftLink(long pointer);
	
	public long removeParentLink(long pointer);//

	// return the old left pointer.
	public long updateLeftLink(long pointer, long childPointer);

	public long removeRightLink(long pointer);

	// return the old left pointer.
	public long updateRightLink(long pointer, long childPointer);

}
