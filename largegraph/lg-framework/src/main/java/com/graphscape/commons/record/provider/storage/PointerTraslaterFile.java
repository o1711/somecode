/**
 * Jan 4, 2014
 */
package com.graphscape.commons.record.provider.storage;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.graphscape.commons.file.FileI;
import com.graphscape.commons.file.FileManagerI;
import com.graphscape.commons.file.PlainRegionI;
import com.graphscape.commons.file.RowRegionI;
import com.graphscape.commons.file.provider.DefaultRowFile;
import com.graphscape.commons.file.provider.LockableProxyRowFile;
import com.graphscape.commons.record.spi.InternalPosition;

/**
 * @author wuzhen0808@gmail.com <br>
 *         <code>
 * +------------------------------------------------------------+
 * |record type	|segment id		|	physicalpointer		|
 * |------------------------------------------------------------|
 * |1 bytes		|4 bytes		|	8 bytes				|
 * +------------------------------------------------------------+
 * 13 bytes.
 * </code>
 */
public class PointerTraslaterFile extends LockableProxyRowFile {

	private static final byte DELETED = -1;

	private static final int HED1 = 1;

	private static final int HED2 = 4;

	private static final int HED3 = 8;

	private static final int WIDTH = HED1 + HED2 + HED3;// 13

	protected ReadWriteLock lock = new ReentrantReadWriteLock();

	protected FileI file;

	/**
	 * @param t
	 */
	private PointerTraslaterFile(FileI rf) {
		this.file = rf;
	}

	public long addInternalPosition(InternalPosition ip) {
		this.writeLock();
		try {

			long rt = this.getRows();

			// TODO recycle space?
			this.writeByte(rt, 0, ip.getType());
			this.writeInt(rt, HED1, ip.getSegmentId());
			this.writeLong(rt, HED1 + HED2, ip.getPointer());
			return rt;
		} finally {
			this.writeUnlock();
		}

	}

	public boolean remove(long pointer) {
		this.writeLock();
		try {
			this.writeByte(pointer, 0, DELETED);
			return true;
		} finally {
			this.writeUnlock();
		}
	}

	public InternalPosition getInternalPosition(long pointer) {
		this.readLock();
		try {

			if (pointer < 0) {
				return null;
			}

			byte type = this.readByte(pointer, 0);
			if (type == DELETED) {
				return null;
			}
			int segid = this.readInt(pointer, HED1);
			long point = this.readLong(pointer, HED1 + HED2);
			return InternalPosition.valueOf(type, segid, point);
		} finally {
			this.readUnlock();
		}

	}

	public void update(long pointer, InternalPosition ip) {
		this.writeLock();
		try {

			this.writeByte(pointer, 0, ip.getType());
			this.writeInt(pointer, HED1, ip.getSegmentId());
			this.writeLong(pointer, HED1 + HED2, ip.getPointer());

		} finally {
			this.writeUnlock();
		}
	}
	
	public void open(){
		this.file.open();
		PlainRegionI pr = file.getTailRegion();
		RowRegionI rf = new DefaultRowFile(pr);
		rf.setRowWidth(WIDTH);//
		super.target = rf;
	}
	
	public void close(){
		this.file.close();
	}

	public static PointerTraslaterFile valueOf(FileManagerI fileManager, String name) {
		FileI pf = fileManager.getOrCreateFile(name);
		
		return new PointerTraslaterFile(pf);
	}

}
