package com.graphscape.commons.record.provider.segments.tree;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import com.graphscape.commons.file.ByteWindowI;
import com.graphscape.commons.file.FileManagerI;
import com.graphscape.commons.file.provider.DefaultByteWindow;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.record.HeapSegmentI;
import com.graphscape.commons.record.TreeSegmentI;
import com.graphscape.commons.record.provider.segments.heap.DefaultHeapSegment;
import com.graphscape.commons.record.support.SegmentSupport;
import com.graphscape.commons.util.ByteArrayUtil;

/**
 * Record format:
 * <p>
 * <code>
 * Node Head Record:
 * +--------------------------------------------------------------------------------------------+
 * | parent pointer	| index		| child pointers list	| content pointer	| additional head 	|
 * |--------------------------------------------------------------------------------------------|
 * | 8 bytes		| 4 bytes	|  8 * maxChilds bytes	| 8 bytes			| x 				|
 * +--------------------------------------------------------------------------------------------+
 * 
 * 
 * </code>
 * 
 * @author wuzhen
 * 
 */
public class DefaultTreeSegment extends SegmentSupport<HeapSegmentI> implements TreeSegmentI {

	protected static final String PROFILE_ADD_CHILD = DefaultTreeSegment.class.getName() + ".addChild";

	protected static final String PROFILE_GET = DefaultTreeSegment.class.getName() + ".get";

	protected static final String PROFILE_GET_CONTENT_POINTER = DefaultTreeSegment.class.getName()
			+ ".getContentPointer";

	protected static final String PROFILE_UPDATE_PARENT_LINK = DefaultTreeSegment.class.getName()
			+ ".updateParentLink";

	protected static final String PROFILE_REMOVE_PARENT_LINK = DefaultTreeSegment.class.getName()
			+ ".removeParentLink";

	protected static final String PROFILE_GET_CHILDPOINTERARRAY = DefaultTreeSegment.class.getName()
			+ ".getChildPointerArray";

	private static final long NULL = -1;

	private static final int PARENT_POINTER = 0;

	private static final int INDEX = 1;

	private static final int CHILD_POINTER_LIST = 2;

	private static final int CONTENT_POINTER = 3;

	private static final int USER_HEADER = 4;

	private HeapSegmentI heap;

	private static ByteWindowI headerFormat;

	private int maxChildSize;

	private int headLength;

	private int[] headWidths;

	private int[] headOffsets;

	private int additionalHeadWidth;

	private DefaultTreeSegment(HeapSegmentI heap, int maxChildSize, int additionalHeadWidth) {
		super(heap);
		this.additionalHeadWidth = additionalHeadWidth;
		this.heap = heap;
		this.applyProfile(this.heap);//
		this.maxChildSize = maxChildSize;

		this.headWidths = new int[] { 8, 4, 8 * this.maxChildSize, 8, this.additionalHeadWidth };
		this.headOffsets = new int[] { 0, //
				headWidths[0], //
				headWidths[0] + headWidths[1],//
				headWidths[0] + headWidths[1] + headWidths[2],//
				headWidths[0] + headWidths[1] + headWidths[2] + headWidths[3],//
				headWidths[0] + headWidths[1] + headWidths[2] + headWidths[3] + headWidths[4]//
		};

		this.headLength = headOffsets[this.headOffsets.length - 1];//

		this.headerFormat = new DefaultByteWindow("TREE_HEAD", this.headLength)//
				.addChild(new DefaultByteWindow("PARENT_POINTER", 8))//
				.addChild(new DefaultByteWindow("CHILD_POINTER", 8, this.maxChildSize))//
				.addChild(new DefaultByteWindow("CONTENT_POINTER", 8));//
	}

	public static TreeSegmentI valueOf(String name, int maxChildSize, int additionalHeadWidth, FileManagerI fm) {
		HeapSegmentI ts = DefaultHeapSegment.valueOf(name, fm);
		return new DefaultTreeSegment(ts, maxChildSize, additionalHeadWidth);

	}

	protected byte[] getHeader(long pointer) {
		return this.heap.get(pointer);
	}

	@Override
	public long getChildPointer(long parentPointer, int index) {
		this.beforeProfile(PROFILE_GET_CHILDPOINTERARRAY);
		try {
			byte[] header = this.getHeader(parentPointer);
			return this.readChildPointer(header, index);
		} finally {
			this.afterProfile();
		}

	}

	private long[] getChildPointerArray(byte[] header) {

		this.readLock();
		try {

			List<Long> rtL = new ArrayList<Long>();
			for (int i = 0; i < this.maxChildSize; i++) {
				long childI = this.readChildPointer(header, i);

				if (childI == NULL) {
					continue;
				}
				rtL.add(childI);//
			}

			long[] rt = new long[rtL.size()];
			for (int i = 0; i < rtL.size(); i++) {
				rt[i] = rtL.get(i);
			}
			return rt;
		} finally {
			this.readUnlock();
		}
	}

	@Override
	public long getParentPointer(long childPointer) {
		this.readLock();
		try {
			byte[] header = this.heap.get(childPointer);
			return this.readParentPointer(header);
		} finally {
			this.readUnlock();
		}
	}

	@Override
	public byte[] getParent(long childPointer) {
		this.readLock();
		try {
			long parentPointer = this.getParentPointer(childPointer);
			if (parentPointer == NULL) {
				return null;
			}

			return this.get(parentPointer);
		} finally {
			this.readUnlock();
		}
	}

	@Override
	public long update(long pointer, byte[] content) {
		this.writeLock();
		try {
			byte[] header = this.heap.get(pointer);
			long contentPointer = this.readContentPointer(header);
			long newContentPointer = this.heap.update(contentPointer, content);//
			if (newContentPointer != contentPointer) {

				this.writeContentPointer(newContentPointer, header);
				this.doUpdateHeader(pointer, header);//

			}
			return pointer;

		} finally {
			this.writeUnlock();
		}
	}

	@Override
	public long add(byte[] content) {
		throw new GsException("Not supported");
	}

	private long getContentPointer(long pointer) {
		this.beforeProfile(PROFILE_GET_CONTENT_POINTER);
		try {

			this.readLock();
			try {
				byte[] header = this.getHeader(pointer);
				long rt = this.readContentPointer(header);
				return rt;
			} finally {
				this.readUnlock();
			}
		} finally {
			this.afterProfile();
		}
	}

	@Override
	public byte[] get(long pointer) {
		this.beforeProfile(PROFILE_GET);
		try {

			long contentPointer = this.getContentPointer(pointer);
			return this.heap.get(contentPointer);
		} finally {
			this.afterProfile();
		}

	}

	@Override
	public void remove(long pointer) {
		this.doRemoveSubStree(pointer);
	}

	private long doRemoveSubStree(long pointer) {
		return doRemove(true, pointer, 0);
	}

	@Override
	public void clear() {
		this.writeLock();
		try {
			this.heap.clear();
		} finally {
			this.writeUnlock();
		}
	}

	@Override
	public void open() {
		this.heap.open();
	}

	@Override
	public void close() {
		this.writeLock();
		try {
			this.heap.close();
		} finally {
			this.writeUnlock();
		}
	}

	private long readContentPointer(byte[] header) {
		return ByteArrayUtil.readLong(this.headOffsets[CONTENT_POINTER], header);
	}

	private long readParentPointer(byte[] header) {
		return ByteArrayUtil.readLong(headOffsets[PARENT_POINTER], header);
	}

	private int readIndex(byte[] header) {
		return ByteArrayUtil.readInt(headOffsets[INDEX], header);
	}

	private void writeContentPointer(long contentPointer, byte[] header) {
		ByteArrayUtil.writeLong(contentPointer, header, this.headOffsets[CONTENT_POINTER]);
	}

	private void writeParentPointer(long parentPointer, byte[] header) {
		ByteArrayUtil.writeLong(parentPointer, header, this.headOffsets[PARENT_POINTER]);//
	}

	private void writeIndex(int idx, byte[] header) {
		ByteArrayUtil.writeInt(idx, header, this.headOffsets[INDEX]);//
	}

	@Override
	public long removeParentLink(long pointer) {
		this.beforeProfile(PROFILE_REMOVE_PARENT_LINK);
		try {
			byte[] header = this.getHeader(pointer);
			long parentPointer = this.readParentPointer(header);//
			if (parentPointer != NULL) {
				this.doRemoveLink(parentPointer, pointer);
			}

			return parentPointer;
		} finally {
			this.afterProfile();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.record.TreeSegmentI#removeChildLink(long,
	 * int)
	 */
	@Override
	public long removeChildLink(long pointer, int index) {
		byte[] header = this.getHeader(pointer);//

		long rt = this.readChildPointer(header, index);
		if (rt != NULL) {
			this.doRemoveLink(pointer, rt);//
		}
		return rt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.record.TreeSegmentI#addChildLink(long, int,
	 * long)
	 */
	@Override
	public void addChildLink(long parentPointer, int idx, long childPointer) {
		this.addLink(parentPointer, idx, childPointer);
	}

	@Override
	public long updateParentLink(long pointer, long newParentPointer) {
		this.beforeProfile(PROFILE_UPDATE_PARENT_LINK);
		try {
			byte[] header = this.getHeader(pointer);
			long parentPointer = this.readParentPointer(header);//
			int index = this.readIndex(header);
			if (parentPointer != NULL) {
				this.removeParentLink(pointer);
			}

			this.addLink(newParentPointer, index, pointer);
			return parentPointer;
		} finally {
			this.afterProfile();
		}

	}

	@Override
	public long add(long parentPointer, int idx, byte[] aheader, byte[] content) {
		this.beforeProfile(PROFILE_ADD_CHILD);
		try {

			return this.doAdd(parentPointer, idx, aheader, content);

		} finally {
			this.afterProfile();
		}
	}

	private void writeAdditionalHeader(byte[] header, byte[] aheader) {
		ByteArrayUtil.write(aheader, 0, header, this.headOffsets[USER_HEADER], aheader.length);
	}

	private long doAdd(long parentPointer, int idx, byte[] aheader, byte[] content) {

		this.writeLock();
		try {

			long contentPointer = this.heap.add(content);
			// create a header
			byte[] header = new byte[this.headLength];//

			this.writeParentPointer(NULL, header);
			this.writeIndex(-1, header);
			this.writeChildPointerArrayAsNull(header);
			this.writeContentPointer(contentPointer, header);
			this.writeAdditionalHeader(header, aheader);
			long rt = this.heap.add(header);// this is pointer to return.
			if (parentPointer == NULL) {
				return rt;
			}
			byte[] parentHeader = this.getHeader(parentPointer);
			this.addLink(parentPointer, parentHeader, idx, rt, header, true);
			return rt;
		} finally {
			this.writeUnlock();
		}
	}

	private void writeChildPointerArrayAsNull(byte[] header) {

		for (int i = 0; i < this.maxChildSize; i++) {
			this.writeChildPointer(header, i, NULL);
		}
	}

	private long readChildPointer(byte[] header, int idx) {
		return ByteArrayUtil.readLong(this.headOffsets[CHILD_POINTER_LIST] + idx * 8, header);//
	}

	private void writeChildPointer(byte[] header, int idx, long pointer) {
		ByteArrayUtil.writeLong(pointer, header, this.headOffsets[CHILD_POINTER_LIST] + idx * 8);
	}

	private void addLink(long parentPointer, int idx, long pointer) {
		byte[] parentHeader = this.heap.get(parentPointer);
		byte[] childHeader = this.heap.get(pointer);
		addLink(parentPointer, parentHeader, idx, pointer, childHeader, true);
	}

	private void addLink(long parentPointer, byte[] parentHeader, int idx, long pointer, byte[] childHeader,
			boolean doUpdate) {

		// new parent

		long child = this.readChildPointer(parentHeader, idx);
		if (child != NULL) {
			throw new GsException("child in index:" + idx + " not empty");
		}
		this.writeChildPointer(parentHeader, idx, pointer);

		// and finally update the header of parent.

		long parent = this.readParentPointer(childHeader);
		if (parent != NULL) {
			throw new GsException("child:" + pointer + ",has oldParent:" + parent);
		}
		this.writeParentPointer(parentPointer, childHeader);
		this.writeIndex(idx, childHeader);//

		if (doUpdate) {
			this.doUpdateHeader(parentPointer, parentHeader);
			this.doUpdateHeader(pointer, childHeader);//
		}
	}

	private void doUpdateHeader(long pointer, byte[] header) {
		long newP = this.heap.update(pointer, header);//

		if (newP != pointer) {
			throw new GsException("this is header, should not change this pointer.");
		}

	}

	private void doRemoveLink(long parentPointer, long pointer) {

		byte[] childHeader = this.heap.get(pointer);
		this.writeParentPointer(NULL, childHeader);
		int index = this.readIndex(childHeader);
		this.writeIndex(-1, childHeader);
		this.doUpdateHeader(pointer, childHeader);
		//

		byte[] parentHeader = this.heap.get(parentPointer);

		this.writeChildPointer(parentHeader, index, NULL);//

		this.doUpdateHeader(parentPointer, parentHeader);

	}

	private long doRemove(boolean removeTree, long pointer, int depth) {
		this.writeLock();
		try {
			byte[] header = this.heap.get(pointer);

			// remove from parent
			long parentPointer = this.readParentPointer(header);

			if (parentPointer != NULL) {// remove from parent.
				this.doRemoveLink(parentPointer, pointer);
			}

			// finally, remove child list,the header and content.
			if (removeTree) {

				// recursively remove child,from leaf node first.
				for (int i = 0; i < this.maxChildSize; i++) {
					long childI = this.readChildPointer(header, i);
					if (childI == NULL) {
						continue;
					}
					this.doRemove(true, childI, depth + 1);//
				}

				long contentPointer = this.readContentPointer(header);
				this.heap.remove(contentPointer);// release content
				this.heap.remove(pointer);// remove header
			}
			return parentPointer;

		} finally {
			this.writeUnlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.record.TreeSegmentI#updateChildLink(long,
	 * long, long)
	 */
	@Override
	public void switchParentLink(long pointer1, long pointer2) {

		long parent1 = this.removeParentLink(pointer1);
		long parent2 = this.removeParentLink(pointer2);

		this.updateParentLink(pointer1, parent2);
		this.updateParentLink(pointer2, parent1);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.commons.record.support.SegmentSupport#getUnderlyingContent
	 * (long)
	 */
	@Override
	public byte[] getUnderlyingContent(long pointer) {

		return this.getHeader(pointer);

	}

	@Override
	public void dumpInternal(Writer writer) throws IOException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.graphscape.commons.record.TreeSegmentI#updateUserHeader(byte[])
	 */
	@Override
	public long updateUserHeader(long pointer,byte[] userHeader) {
		byte[] header = this.getHeader(pointer);
		this.writeAdditionalHeader(header, userHeader);
		this.doUpdateHeader(pointer, header);
		return pointer;
		
	}

	/* (non-Javadoc)
	 * @see com.graphscape.commons.record.TreeSegmentI#getUserHeader(long)
	 */
	@Override
	public byte[] getUserHeader(long pointer) {
		byte[] header = this.getHeader(pointer);
		return ByteArrayUtil.subArray(header, this.headOffsets[USER_HEADER]);
		
	}

}
