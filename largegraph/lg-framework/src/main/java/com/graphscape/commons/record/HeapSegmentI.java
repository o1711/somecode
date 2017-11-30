/**
 * Jan 4, 2014
 */
package com.graphscape.commons.record;


/**
 * @author wuzhen0808@gmail.com <br>
 *         Segment is the underlying physical storage.it manage simply byte[] as
 *         the elements.the pointer value may jump.
 */
public interface HeapSegmentI extends SegmentI{

	public long update(long pointer, byte[] content);

	public long add(byte[] content);

	public byte[] get(long pointer);

	public void remove(long pointer);

	public void clear();
	
}
