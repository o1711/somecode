package com.graphscape.commons.record.provider.segments.tree;

import java.io.IOException;
import java.io.Writer;

import com.graphscape.commons.file.ByteWindowI;
import com.graphscape.commons.file.FileManagerI;
import com.graphscape.commons.file.PlainRegionI;
import com.graphscape.commons.file.provider.DefaultByteWindow;
import com.graphscape.commons.file.provider.MemoryPlainRegion;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.record.BiTreeSegmentI;
import com.graphscape.commons.record.TreeSegmentI;
import com.graphscape.commons.record.provider.segments.tree.formaters.DefaultEntryFormater;
import com.graphscape.commons.record.provider.segments.tree.formaters.TypeFormater;
import com.graphscape.commons.record.support.ProxySegementSupport;
import com.graphscape.commons.util.ByteArrayUtil;

/**
 * Root pointer saved in header. <br>
 * <code>
 * 
 * +--------------------+
 * | left/right	|  ctt	|
 * |--------------------|
 * | 1 byte		|  	*	|
 * +--------------------+
 * 
 * </code>
 * 
 * @author wuzhen
 * 
 */
public class DefaultBinaryTreeSegment extends ProxySegementSupport<TreeSegmentI> implements BiTreeSegmentI {

	public static final String PROFILE_PUT = DefaultBinaryTreeSegment.class.getName() + ".addChild";

	public static final int LEFT = 0;// is the left child
	public static final int RIGHT = 1;// is the right child
	public static final int ROOT = -1;// is root,no parent

	private static final long NULL = -1;

	private boolean debug = true;

	private static final String HEADER_REGION = "default-binary-tree-segment-header";

	private static final int headerRegionLength = 8;//

	private static final long HPOINTER_ROOT = 0;//

	private ByteWindowI window;

	private PlainRegionI header;

	private static final int HED1 = 1;

	private static final int TWO = 2;

	private int additionalHeaderWidth;
	
	public static final String WNAME_DIRECT = "DIRECT";

	private DefaultBinaryTreeSegment(TreeSegmentI target, int additionalHeaderWidth, ByteWindowI contentWindow) {
		super(target);
		this.additionalHeaderWidth = additionalHeaderWidth;
		this.window = new DefaultByteWindow("NODE", 1)//
				.addChild(new DefaultByteWindow(WNAME_DIRECT, 4).formater(new TypeFormater()))//
				.addChild(contentWindow);
		this.applyProfile(target);//

	}

	public static DefaultBinaryTreeSegment valueOf(String name, int additionalHeaderWidth,
			ByteWindowI contentWindow, FileManagerI fm) {
		TreeSegmentI target = DefaultTreeSegment.valueOf(name, TWO, additionalHeaderWidth, fm);

		return new DefaultBinaryTreeSegment(target, additionalHeaderWidth, contentWindow);
	}

	@Override
	public long addLeft(long parentPointer, byte[] header, byte[] content) {
		return this.target.add(parentPointer, LEFT, header, content);
	}

	private boolean isLeft(byte[] hcontent) {
		return isLeft(true, hcontent);
	}

	private boolean isRight(byte[] hcontent) {
		return isLeft(false, hcontent);
	}

	private boolean isLeft(boolean left, byte[] hcontent) {
		return hcontent[0] == (left ? LEFT : RIGHT);
	}

	@Override
	public long addRight(long parentPointer, byte[] header, byte[] content) {
		return this.target.add(parentPointer, RIGHT, header, content);
	}

	@Override
	public long getLeftPointer(long parentPointer) {
		return this.target.getChildPointer(parentPointer, LEFT);
	}

	@Override
	public long getRightPointer(long parentPointer) {
		return this.target.getChildPointer(parentPointer, RIGHT);
	}

	@Override
	public long removeLeftLink(long pointer) {

		return this.target.removeChildLink(pointer, LEFT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.commons.record.BinaryTreeSegmentI#removeRightLink(long)
	 */
	@Override
	public long removeRightLink(long pointer) {

		return this.target.removeChildLink(pointer, RIGHT);

	}

	@Override
	public long updateLeftLink(long pointer, long childPointer) {
		long rt = this.removeLeftLink(pointer);// old left.
		this.target.addChildLink(pointer, LEFT, childPointer);//
		return rt;
	}

	@Override
	public long updateRightLink(long parentPointer, long childPointer) {

		long rt = this.removeRightLink(parentPointer);// old right
		this.target.addChildLink(parentPointer, RIGHT, childPointer);//

		return rt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.record.BinaryTreeSegmentI#getRootPointer()
	 */
	@Override
	public long getRootPointer() {
		long rt = this.header.readLong(HPOINTER_ROOT);

		return rt;
	}

	@Override
	public long setRootPointer(long root) {
		long rt = this.getRootPointer();
		this.doSetRootPointer(root);
		return rt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.commons.record.BinaryTreeSegmentI#getParentPointer(long)
	 */
	@Override
	public long getParentPointer(long pointer) {

		return this.target.getParentPointer(pointer);
	}

	private void doSetRootPointer(long pointer) {
		this.header.writeLong(HPOINTER_ROOT, pointer);//
	}

	public boolean isEmpty() {
		return NULL == this.getRootPointer();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.record.BinaryTreeSegmentI#addRoot(byte[])
	 */
	@Override
	public long addRoot(byte[] header, byte[] content) {
		// TODO Auto-generated method stub
		if (!this.isEmpty()) {
			throw new GsException("duplicated root");
		}

		long rt = this.target.add(NULL, -1, header, content);
		this.doSetRootPointer(rt);
		return rt;

	}

	@Override
	public void remove(long pointer) {
		this.target.remove(pointer);
		if (this.getRootPointer() == pointer) {
			this.doSetRootPointer(NULL);//
		}
	}

	@Override
	public long removeParentLink(long pointer) {
		return this.target.removeParentLink(pointer);
	}

	@Override
	public long update(long pointer, byte[] content) {
		long rt = this.target.update(pointer, content);
		if (rt != pointer) {
			throw new GsException("pointer should not be changed.");
		}
		return pointer;
	}

	@Override
	public byte[] get(long pointer) {
		byte[] content = this.target.get(pointer);

		return content;
	}

	@Override
	public void dumpInternal(Writer writer) throws IOException {

		long root = this.getRootPointer();
		if (root == NULL) {
			writer.write("<null>");
		}
		int maxDepth = this.getMaxDepth();
		this.dump(0, ROOT,maxDepth, root, writer);
	}

	private int getMaxDepth() {
		return getMaxDepth(this.getRootPointer());//
	}

	private int getMaxDepth(long pointer) {
		if (pointer == NULL) {
			return 0;
		}
		long left = this.getLeftPointer(pointer);
		int maxLeft = this.getMaxDepth(left);

		long right = this.getRightPointer(pointer);
		int maxRight = this.getMaxDepth(right);
		return Math.max(maxLeft, maxRight) + 1;

	}

	private void dump(int depth,int direct, int maxDepth, long pointer, Writer writer) throws IOException {

		if (pointer == NULL) {
			// ignore empty.
			return;
		}
		byte[] dir = ByteArrayUtil.writeInt(direct);//
		byte[] content = this.get(pointer);
		byte[] header = this.target.getUserHeader(pointer);
		
		byte[] content2 = ByteArrayUtil.concate(dir, header,content);
		
		PlainRegionI pr = new MemoryPlainRegion(content2);//

		new DefaultEntryFormater(depth, maxDepth).format(window, pr, writer);

		long lpointer = this.getLeftPointer(pointer);
		this.dump(depth + 1,LEFT, maxDepth, lpointer, writer);

		long rpointer = this.getRightPointer(pointer);
		this.dump(depth + 1,RIGHT, maxDepth, rpointer, writer);

	}

	@Override
	public void clear() {
		this.target.clear();
		this.refresh();

	}

	@Override
	public void open() {
		super.open();
		this.refresh();
	}

	private void refresh() {
		this.header = this.target.getRegion(HEADER_REGION);
		if (this.header == null) {
			this.header = this.target.addRegion(HEADER_REGION, this.headerRegionLength);
			this.doSetRootPointer(NULL);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.record.BiTreeSegmentI#updateUserHeader(long,
	 * byte[])
	 */
	@Override
	public long updateUserHeader(long pointer, byte[] header) {
		return this.target.updateUserHeader(pointer, header);
	}

	/* (non-Javadoc)
	 * @see com.graphscape.commons.record.BiTreeSegmentI#getUserHeader(long)
	 */
	@Override
	public byte[] getUserHeader(long pointer) {
		return this.target.getUserHeader(pointer);//
	}

}
