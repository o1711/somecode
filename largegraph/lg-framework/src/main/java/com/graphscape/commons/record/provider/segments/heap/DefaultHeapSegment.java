/**
 * Jan 4, 2014
 */
package com.graphscape.commons.record.provider.segments.heap;

import java.io.IOException;
import java.io.Writer;

import com.graphscape.commons.file.ByteWindowI;
import com.graphscape.commons.file.FileI;
import com.graphscape.commons.file.FileManagerI;
import com.graphscape.commons.file.PlainRegionI;
import com.graphscape.commons.file.provider.DefaultByteWindow;
import com.graphscape.commons.file.support.ProxyPlainRegion2;
import com.graphscape.commons.record.HeapSegmentI;
import com.graphscape.commons.record.support.SegmentSupport;
import com.graphscape.commons.util.ByteArrayUtil;

/**
 * @author wuzhen0808@gmail.com <br>
 *         <code>
 * +----------------------------+
 * | Size		| Content		|
 * |------------|---------------|
 * | 4 bytes	| 0+			|
 * +----------------------------+	
 * </code>
 */
public class DefaultHeapSegment extends SegmentSupport<FileI> implements HeapSegmentI {

	protected FileI file;

	protected PlainRegionI main;

	protected RoomManager freeBlocks;

	protected long dataLength;

	protected static final String PROFILE_ADD = DefaultHeapSegment.class.getName() + ".add";

	protected static final String PROFILE_GET = DefaultHeapSegment.class.getName() + ".get";

	protected static final String PROFILE_REMOVE = DefaultHeapSegment.class.getName() + ".remove";

	protected static final int HED1 = 4;
	private static ByteWindowI FORMAT = new DefaultByteWindow("HEAP")//
			.addChild(new DefaultByteWindow("SIZE", HED1))//
			.addChild(new DefaultByteWindow("HH", HED1, "SIZE"));

	private DefaultHeapSegment(FileI file, RoomManager fbs) {
		super(file);
		this.file = file;
		this.freeBlocks = fbs;
		this.applyProfile(fbs);
	}

	public static DefaultHeapSegment valueOf(String name, FileManagerI fm) {

		FileI pf = fm.getOrCreateFile(name + ".2.seg");
		RoomManager fbs = RoomManager.valueOf(name + ".1.seg", fm);

		return new DefaultHeapSegment(pf, fbs);

	}

	@Override
	public void remove(long pointer) {
		this.beforeProfile(PROFILE_REMOVE);
		try {
			this.writeLock();
			try {
				int size = this.main.readInt(pointer);
				this.freeBlocks.add(pointer, size);

			} finally {
				this.writeUnlock();
			}
		} finally {
			this.afterProfile();
		}
	}

	@Override
	public long update(long pointer, byte[] content) {
		this.writeLock();
		try {
			int size = this.main.readInt(pointer);
			int newSize = content.length;
			if (newSize != size) {// only need to update size
				if (newSize < size) {// have enough space,release the reamin
										// space.
					this.freeBlocks.add(pointer + HED1 + newSize, size - newSize);
					// free remains space

				} else {// new size > old one.
					// release and allocate new space.
					this.freeBlocks.add(pointer, size + HED1);
					// allocate new pointer.
					pointer = this.allocate(newSize);

				}
				// WRITE size
				this.main.writeInt(pointer, newSize);
			}

			// WRITE content.
			this.main.write(pointer + HED1, content);//

			return pointer;
		} finally {
			this.writeUnlock();
		}
	}

	private long allocate(int size) {
		long rt = this.freeBlocks.allocate(size + HED1);
		if (rt == -1) {// no space freed before,so append to the end of the file
			rt = this.main.getLength();
		}

		return rt;
	}

	@Override
	public long add(byte[] content) {
		this.beforeProfile(PROFILE_ADD);
		try {

			this.writeLock();
			try {
				long rt = this.allocate(content.length);
				this.main.writeInt(rt, content.length);
				this.main.write(rt + HED1, content);
				// byte[] content2 =
				// ByteArrayUtil.concate(ByteArrayUtil.writeInt(content.length),content);
				// this.main.write(rt, content2);//
				return rt;

			} finally {
				this.writeUnlock();
			}
		} finally {
			this.afterProfile();
		}

	}

	@Override
	public byte[] get(long pointer) {
		this.beforeProfile(PROFILE_GET);
		try {
			this.readLock();
			try {
				int size = this.main.readInt(pointer);
				byte[] rt = new byte[size];
				this.main.read(pointer + HED1, rt);
				return rt;
			} finally {
				this.readUnlock();
			}
		} finally {
			this.afterProfile();
		}
	}

	protected int readLenth(long pointer) {
		int size = this.main.readInt(pointer);
		return size;
	}

	@Override
	public void open() {
		this.file.open();
		this.freeBlocks.open();
		this.refresh();
	}

	private void refresh() {
		this.main = file.getTailRegion();
		this.applyProfile(this.main);
	}

	@Override
	public void close() {
		this.file.close();
		this.freeBlocks.close();
	}

	@Override
	public void clear() {
		this.file.clear();
		this.freeBlocks.clear();
		this.refresh();
	}

	@Override
	public void dumpInternal(Writer writer) throws IOException {

	}
	/**
	 * <code>
	public PlainRegionI getAsRegion(long pointer) {
		this.readLock();
		try {
			
			 // int size = this.main.readInt(pointer); byte[] rt = new
			 // byte[size]; this.main.read(pointer + HED1, rt);
			 
			int len = this.main.readInt(pointer);//
			PlainRegionI rt = new ProxyPlainRegion2(this.main, pointer + HED1, len);
			return rt;
		} finally {
			this.readUnlock();
		}
	}
	 * 
	 * @param pointer
	 * @return
	 */
}
