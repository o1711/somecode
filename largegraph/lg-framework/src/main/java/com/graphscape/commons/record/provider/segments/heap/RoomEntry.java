/**
 * Jan 4, 2014
 */
package com.graphscape.commons.record.provider.segments.heap;

import com.graphscape.commons.file.FileI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class RoomEntry {

	protected long entryPointer;

	protected long dataPointer;

	protected int size;

	private RoomEntry(long pointer, long dpointer, int size) {
		this.entryPointer = pointer;
		this.size = size;
		this.dataPointer = dpointer;
	}

	public long getEntryPointer() {
		return entryPointer;
	}

	public void setEntryPointer(long pointer) {
		this.entryPointer = pointer;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public static RoomEntry valueOf(long pointer, long pointer2, int size2) {
		return new RoomEntry(pointer, pointer2, size2);
	}

	public long getDataPointer() {
		return dataPointer;
	}

	public void setDataPointer(long dataPointer) {
		this.dataPointer = dataPointer;
	}
}
