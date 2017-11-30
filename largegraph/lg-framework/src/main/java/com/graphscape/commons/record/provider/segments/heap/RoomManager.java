/**
 * Jan 4, 2014
 */
package com.graphscape.commons.record.provider.segments.heap;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import com.graphscape.commons.file.FileI;
import com.graphscape.commons.file.FileManagerI;
import com.graphscape.commons.file.PlainRegionI;
import com.graphscape.commons.file.provider.ProxyPlainFile;

/**
 * @author wuzhen0808@gmail.com <br>
 *         <code>
 *         +________________________+
 *         |pointer 	| size		|
 *         |8 byte 		| 4 byte 	|
 *         +________________________+
 *         
 * </code>
 * 
 *         TODO use ByteWindowI to implement this.
 */
public class RoomManager extends ProxyPlainFile {

	private static final String PROFILE_ADD = RoomManager.class.getName() + ".add";

	private static final long DELETED = -1;

	private static final String PROFILE_ALLOCATE = RoomManager.class.getName() + ".allocate";

	public static final long HED1 = 8;
	public static final long HED2 = 4;

	private NavigableMap<Integer, List<RoomEntry>> roomMapBySize = new TreeMap<Integer, List<RoomEntry>>();

	private List<Long> entryPointerFactory = new LinkedList<Long>();//
	private PlainRegionI region;

	/**
	 * @param t
	 */
	private RoomManager(FileI t) {
		super(t);
	}

	public static RoomManager valueOf(String name, FileManagerI fm) {
		FileI pf = fm.getOrCreateFile(name);

		return new RoomManager(pf);
	}

	@Override
	public void open() {
		super.open();
		this.region = this.target.getTailRegion();
		this.applyProfile(this.region);//
	}

	/**
	 * @return the data pointer for the space allocated. return -1 if cannot
	 *         allocate the required space.
	 */
	public long allocate(int size) {
		this.beforeProfile(PROFILE_ALLOCATE);
		try {
			return this.doAllocate(size);
		} finally {
			this.afterProfile();
		}
	}

	private long doAllocate(int size) {

		Map.Entry<Integer, List<RoomEntry>> available = this.roomMapBySize.ceilingEntry(size);
		if (available == null) {
			return -1;
		}
		// remove from map.
		List<RoomEntry> rL = available.getValue();
		RoomEntry theRec = rL.remove(0);// remove from list.
		if (rL.isEmpty()) {// release from map.
			this.roomMapBySize.remove(available.getKey());
		}

		//
		long rt = theRec.getDataPointer();//
		// check the remain space.
		int newSize = theRec.getSize() - size;
		if (newSize == 0) {// remove

			this.collectEntry(theRec.entryPointer);//

		} else {// large than,update the record and save it to map.

			theRec.setDataPointer(theRec.dataPointer + size);//
			theRec.setSize(newSize);
			List<RoomEntry> newSizeList = this.roomMapBySize.get(newSize);
			if (newSizeList == null) {
				newSizeList = new LinkedList<RoomEntry>();
				this.roomMapBySize.put(newSize, newSizeList);
			}
			newSizeList.add(theRec);
		}

		return rt;
	}

	private void collectEntry(long entryPointer) {
		this.entryPointerFactory.add(entryPointer);//
		this.region.writeLong(entryPointer, -1);// empty entry, can be

	}

	private void write(long entryPointer, long dataPointer, int dataSize) {
		this.region.writeLong(entryPointer, dataPointer);
		this.region.writeInt(entryPointer + 8, dataSize);
	}

	public void add(long dataPointer, int dataSize) {
		this.beforeProfile(PROFILE_ADD);
		try {

			if (!this.entryPointerFactory.isEmpty()) {
				long entryPointer = this.entryPointerFactory.remove(0);//
				this.write(entryPointer, dataPointer, dataSize);
			} else {// expand the file.
				long entryPointer = this.region.getLength();
				this.write(entryPointer, dataPointer, dataSize);
			}
		} finally {
			this.afterProfile();
		}

		// this.recordList.add(rec);
	}

}
