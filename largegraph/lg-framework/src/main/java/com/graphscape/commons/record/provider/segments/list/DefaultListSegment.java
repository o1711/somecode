package com.graphscape.commons.record.provider.segments.list;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import com.graphscape.commons.file.ByteWindowI;
import com.graphscape.commons.file.FileI;
import com.graphscape.commons.file.FileManagerI;
import com.graphscape.commons.file.RowRegionI;
import com.graphscape.commons.file.provider.DefaultByteWindow;
import com.graphscape.commons.file.provider.DefaultRowFile;
import com.graphscape.commons.record.ListSegmentI;
import com.graphscape.commons.record.support.SegmentSupport;

/**
 * <code>
 * +----------------------------------------------------+
 * | type	| pre pointer	| next pointer	| content	|
 * |----------------------------------------------------|
 * | 1 byte	| 8 bytes		| 8 bytes		| fix Width	|
 * +----------------------------------------------------+
 * 
 * type 0: master for content list.
 * type 1: master for free list.
 * type 2: content list element.
 * type 3: free list element.
 * 
 * type 0/1:
 * 
 * +----------------------------------------------------+
 * | type	| first pointer	| laster pointer	| any	|
 * |----------------------------------------------------|
 * | 0		| 8 bytes		| 8 bytes			| 8+	|
 * +----------------------------------------------------+
 * 
 * </code>
 * 
 * @author wuzhen
 * 
 */
public class DefaultListSegment extends SegmentSupport<FileI> implements ListSegmentI {

	private static final int HED1 = 1;
	private static final int HED2 = 8;
	private static final int HED3 = 8;
	private static final int HED4 = 8;

	private static final byte TYPE_CONTENT_LIST = 0;;
	private static final byte TYPE_FREE_LIST = 1;
	private static final byte TYPE_CONTENT_ELEMENT = 2;
	private static final byte TYPE_FREE_ELEMENT = 3;

	private static final int CONTENT = 0;// ROW number for content list
											// head/tail pointer.
	private static final int FREE = 1;// row number for free list head/tail
										// pointer.

	private static final long NULL = -1;

	protected RowRegionI rows;

	protected int fixContentWidth;

	protected long headPointers[] = new long[] { -1, -1 };

	protected long tailPointers[] = new long[] { -1, -1 };

	protected int totalWidth;
	private ByteWindowI format;

	private DefaultListSegment(FileI file, int fixContentWidth) {
		super(file);

		this.rows = new DefaultRowFile(file.getTailRegion());//

		this.rows.setRowWidth(HED1 + HED2 + HED3 + fixContentWidth);

		this.fixContentWidth = fixContentWidth;

		if (this.fixContentWidth < HED4) {
			throw new IllegalArgumentException();
		}
		this.totalWidth = this.rows.getRowWidth();
		if (this.totalWidth != HED1 + HED2 + HED3 + fixContentWidth) {
			throw new IllegalArgumentException();
		}
		format = new DefaultByteWindow("ROW", this.totalWidth)//
				.addChild(new DefaultByteWindow("TPCD", HED1))//
				.addChild(new DefaultByteWindow("PREP", HED2))//
				.addChild(new DefaultByteWindow("NEXP", HED3))//
				.addChild(new DefaultByteWindow("CTT", fixContentWidth));
	}

	public static DefaultListSegment valueOf(String name, int fixContentWidth, FileManagerI fm) {
		// , HED1 + HED2 + HED3 + fixContentWidth
		FileI file = fm.getOrCreateFile(name);
		return new DefaultListSegment(file, fixContentWidth);

	}

	@Override
	public long update(long pointer, byte[] content) {

		this.rows.write(pointer, HED1 + HED2 + HED3, content);
		return pointer;
	}

	/**
	 * Append to the last position.
	 */
	@Override
	public long add(byte[] content) {

		long pre = this.getLasterPointer();
		return this.doInsert(pre, content);
	}

	@Override
	public byte[] get(long pointer) {
		byte[] buffer = new byte[this.fixContentWidth];
		this.rows.read(pointer, HED1 + HED2 + HED3, buffer);
		return buffer;
	}

	@Override
	public void clear() {
		this.target.clear();
		this.headPointers[FREE] = NULL;
		this.tailPointers[FREE] = NULL;
		this.headPointers[CONTENT] = NULL;
		this.tailPointers[CONTENT] = NULL;
	}

	@Override
	public void open() {
		super.open();
		if (this.rows.getRows() == 0) {// init

			this.rows.writeByte(CONTENT, 0, DefaultListSegment.TYPE_CONTENT_LIST);
			this.rows.writeLong(CONTENT, HED1, DefaultListSegment.NULL);
			this.rows.writeLong(CONTENT, HED1 + HED2, DefaultListSegment.NULL);

			this.rows.writeByte(FREE, 0, DefaultListSegment.TYPE_FREE_LIST);
			this.rows.writeLong(FREE, HED1, DefaultListSegment.NULL);
			this.rows.writeLong(FREE, HED1 + HED2, DefaultListSegment.NULL);
		} else {// cache pointers.
			this.headPointers[CONTENT] = this.rows.readLong(CONTENT, HED1);
			this.headPointers[CONTENT] = this.rows.readLong(CONTENT, HED1 + HED2);
			this.headPointers[FREE] = this.rows.readLong(FREE, HED1);
			this.headPointers[FREE] = this.rows.readLong(FREE, HED1 + HED2);
		}
	}

	@Override
	public void close() {
		this.target.close();
	}

	@Override
	public long getFirstPointer() {

		return this.headPointers[CONTENT];
	}

	@Override
	public long getNextPointer(long pointer) {
		return this.rows.readLong(pointer, HED1 + HED2);
	}

	@Override
	public long getPrePointer(long pointer) {
		return this.rows.readLong(pointer, HED1);
	}

	@Override
	public long getLasterPointer() {

		return this.tailPointers[CONTENT];
	}

	@Override
	public long insert(long prePointer, byte[] content) {
		return this.doInsert(prePointer, content);
	}

	private long doInsert(long prePointer, byte[] content) {
		long rt = this.allocate();
		this.doInsert(CONTENT, prePointer, rt);
		this.rows.write(rt, HED1 + HED2 + HED3, content);
		return rt;
	}

	@Override
	public void remove(long pointer) {
		long pre = this.readPrePointer(pointer);
		long next = this.readNextPointer(pointer);
		if (pre == NULL) {// no pre,remove the head.
			// the new head is the next.
			this.writeHeadPointer(CONTENT, next);
		}
		if (next == NULL) {
			// no next,remove the last
			// the new tail is the pre.
			this.writeTailPointer(CONTENT, pre);
		}
		// insert into the free list
		this.doInsert(FREE, this.tailPointers[FREE], pointer);
	}

	private long readPrePointer(long pointer) {
		return this.rows.readLong(pointer, HED1);
	}

	private long readNextPointer(long pointer) {
		return this.rows.readLong(pointer, HED1 + HED2);
	}

	private void writeHeadPointer(int list, long pointer) {
		this.headPointers[list] = pointer;
		this.rows.writeLong(list, HED1, pointer);
	}

	private void writeTailPointer(int list, long pointer) {
		this.headPointers[list] = pointer;
		this.rows.writeLong(list, HED1 + HED2, pointer);
	}

	private void writePrePointer(long pointer, long prePointer) {
		this.rows.writeLong(pointer, HED1, prePointer);
	}

	private void writeNextPointer(long pointer, long nextPointer) {
		this.rows.writeLong(pointer, HED1 + HED2, nextPointer);
	}

	private long allocate() {
		// alocate,move from free list.

		long rt = this.headPointers[FREE];

		if (rt == NULL) {// no free,expand file.

			rt = this.rows.getRows();

		} else if (rt == this.tailPointers[FREE]) {
			// only one free element,so
			// empty the free list.
			this.writeHeadPointer(FREE, NULL);
			this.writeTailPointer(FREE, NULL);
		} else {// has more element free,no need to update the last pointer?
				//
			// now the first free updated.
			long second = this.rows.readLong(this.headPointers[FREE], HED1 + HED2);
			this.writeHeadPointer(FREE, second);
			// update the pre of the new first.
			// no pre pointer now,it is the head
			this.writePrePointer(second, NULL);

			// update to file header.
			// update the new first pointer.
		}
		return rt;
	}

	private void doInsert(int list, long prePointer, long thePointer) {

		// insert the new element to content list :
		// get the old next
		long next = -1;
		if (prePointer == NULL) {// new element is the first element.
			next = this.headPointers[list];//
			this.writeHeadPointer(list, thePointer);
		} else {// the pre element exist,so update it's next to the new element.
			next = this.rows.readLong(prePointer, HED1 + HED2);
			// the new element as the next of the pre .
			this.writeNextPointer(prePointer, thePointer);
		}

		if (next == NULL) {// new element is the last element
			this.writeTailPointer(list, thePointer);
		} else {
			// the new element as the pre of the old next.
			this.writePrePointer(next, thePointer);
		}

		// also update the new element's pre
		this.writePrePointer(prePointer, thePointer);
		// and update the next of the new element
		this.writeNextPointer(thePointer, next);
		// update the content.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.record.ListSegmentI#removeByContent(byte[])
	 */
	@Override
	public boolean removeByContent(byte[] content) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long[] getPointerList() {
		List<Long> rtL = new ArrayList<Long>();
		long pointer = this.headPointers[CONTENT];

		while (pointer != NULL) {
			rtL.add(pointer);
			pointer = this.getNextPointer(pointer);
		}

		long[] rt = new long[rtL.size()];
		for (int i = 0; i < rtL.size(); i++) {
			rt[i] = rtL.get(i);
		}
		return rt;
	}

	@Override
	public byte[][] getContentList() {
		long[] pointerL = this.getPointerList();
		byte[][] rt = new byte[pointerL.length][];

		for (int i = 0; i < pointerL.length; i++) {
			rt[i] = this.get(pointerL[i]);
		}
		return rt;
	}

	@Override
	public void dumpInternal(Writer writer) throws IOException {
		// TODO Auto-generated method stub

	}

}
