/**
 * Jan 4, 2014
 */
package com.graphscape.commons.record.spi;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class InternalPosition {

	private int segmentId;

	private byte type;

	private long pointer;

	/**
	 * @param segid
	 * @param point
	 */
	public InternalPosition(byte type, int segid, long point) {
		this.segmentId = segid;
		this.pointer = point;
		this.type = type;
	}

	public int getSegmentId() {
		return segmentId;
	}

	public long getPointer() {
		return pointer;
	}

	public static InternalPosition valueOf(byte type, int segid, long point) {
		return new InternalPosition(type, segid, point);

	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public void setSegmentId(int segmentId) {
		this.segmentId = segmentId;
	}

	public void setPointer(long pointer) {
		this.pointer = pointer;
	}

}
