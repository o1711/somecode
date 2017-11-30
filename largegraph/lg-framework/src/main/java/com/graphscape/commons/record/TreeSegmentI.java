package com.graphscape.commons.record;

/**
 * This segment allow multiple root node. and not save them in the underlying
 * file.
 * 
 * @author wuzhen
 * 
 */
public interface TreeSegmentI extends SegmentI {

	public long update(long pointer, byte[] content);
	
	public long add(byte[] content);

	public byte[] get(long pointer);

	public void remove(long pointer);

	public void clear();
	
	public long add(long parentPointer,int idx, byte[] header, byte[] content);
	
	public long updateUserHeader(long pointer, byte[] userHeader);
	
	public byte[] getUserHeader(long pointer);

	public long getChildPointer(long pointer, int index);

	public long getParentPointer(long pointer);

	public byte[] getParent(long pointer);
	
	public void switchParentLink(long pointer1,long pointer2);
	
	/**
	 * as root node,not remove the 
	 * @param pointer
	 */
	public long removeParentLink(long pointer);
	
	public long removeChildLink(long pointer, int index);
	
	public void addChildLink(long parentPointer, int index, long childPointer);
	
	public long updateParentLink(long pointer, long newParentPointer);
	
	public byte[] getUnderlyingContent(long pointer) ;

}
