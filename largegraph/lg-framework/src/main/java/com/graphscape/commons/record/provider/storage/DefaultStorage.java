/**
 * Jan 4, 2014
 */
package com.graphscape.commons.record.provider.storage;

import com.graphscape.commons.file.FileManagerI;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.record.HeapSegmentI;
import com.graphscape.commons.record.Position;
import com.graphscape.commons.record.RecordI;
import com.graphscape.commons.record.RecordType;
import com.graphscape.commons.record.StorageI;
import com.graphscape.commons.record.provider.DefaultRecord;
import com.graphscape.commons.record.provider.segments.heap.DefaultHeapSegment;
import com.graphscape.commons.record.spi.InternalPosition;
import com.graphscape.commons.record.support.StorageSupport;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class DefaultStorage extends StorageSupport implements StorageI {
	private HeapSegmentI segment;

	private PointerTraslaterFile pointers;

	private FileManagerI fileManager;

	public DefaultStorage(FileManagerI fm) {
		this.fileManager = fm;
	}

	// index.

	@Override
	public void open() {
		this.fileManager.open();
		this.segment = DefaultHeapSegment.valueOf("data", this.fileManager);
		this.pointers = PointerTraslaterFile.valueOf(this.fileManager, "pointers.idx");
		this.segment.open();
		this.pointers.open();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.record.ResourceI#close()
	 */
	@Override
	public void close() {
		this.pointers.close();
		this.segment.close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.commons.record.StorageI#getRecord(com.graphscape.commons
	 * .record.Position)
	 */
	@Override
	public RecordI getRecord(Position position) {
		this.readLock();
		try {

			InternalPosition ip = this.pointers.getInternalPosition(position.getPointer());
			if (ip == null) {
				return null;
			}

			byte[] content = this.segment.get(ip.getPointer());

			return new DefaultRecord(ip, content);
		} finally {
			this.readUnlock();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.commons.record.StorageI#addRecord(com.graphscape.commons
	 * .record.RecordI)
	 */
	@Override
	public Position addRecord(RecordType type, byte[] content) {
		this.writeLock();
		try {

			long pointer = this.segment.add(content);
			InternalPosition ip = InternalPosition.valueOf(type.getTypeCode(), 0, pointer);
			long rtP = this.pointers.addInternalPosition(ip);
			return Position.valueOf(rtP);
		} finally {
			this.writeUnlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.commons.record.StorageI#updateRecord(com.graphscape.commons
	 * .record.Position, com.graphscape.commons.record.RecordI)
	 */
	@Override
	public void updateRecord(Position position, RecordType type, byte[] content) {
		this.writeLock();
		try {

			InternalPosition ip = this.pointers.getInternalPosition(position.getPointer());
			if (ip == null) {
				throw new GsException("no this record with position:" + position);
			}

			long oldpointer = ip.getPointer();
			long newpointer = this.segment.update(oldpointer, content);
			if (newpointer == oldpointer) {
				return;// internal position not changed.
			}
			ip.setType(type.getTypeCode());
			ip.setPointer(newpointer);

			this.pointers.update(position.getPointer(), ip);
		} finally {
			this.writeUnlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.commons.record.StorageI#removeRecord(com.graphscape.commons
	 * .record.Position)
	 */
	@Override
	public boolean removeRecord(Position position) {
		this.writeLock();
		try {
			InternalPosition ip = this.pointers.getInternalPosition(position.getPointer());
			if (ip == null) {
				return false;
			}
			this.segment.remove(ip.getPointer());
			return this.pointers.remove(position.getPointer());
		} finally {
			this.writeUnlock();
		}

	}

}
