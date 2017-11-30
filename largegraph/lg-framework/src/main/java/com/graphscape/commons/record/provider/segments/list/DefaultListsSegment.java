package com.graphscape.commons.record.provider.segments.list;

import java.io.IOException;
import java.io.Writer;

import com.graphscape.commons.file.FileManagerI;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.record.ListsSegmentI;
import com.graphscape.commons.record.HeapSegmentI;
import com.graphscape.commons.record.provider.segments.heap.DefaultHeapSegment;
import com.graphscape.commons.record.support.SegmentSupport;
import com.graphscape.commons.util.ByteArrayUtil;

/**
 * <code>
 * +----------------------------------------------+
 * | pre pointer | next pointer | element content | 
 * |----------------------------------------------|
 * | 8 bytes	 | 8 bytes		| x				  |
 * +----------------------------------------------+
 * </code>
 * 
 * @author wuzhen
 * 
 */
public class DefaultListsSegment extends SegmentSupport<HeapSegmentI> implements
		ListsSegmentI {

	private static final int WIDTH0 = 8;
	private static final int WIDTH1 = 8;

	private static final int OFFSET1 = WIDTH0;
	private static final int OFFSET2 = WIDTH0 + WIDTH1;

	private static final long NULL = -1;

	public DefaultListsSegment(HeapSegmentI header) {
		super(header);
	}

	public static ListsSegmentI valueOf(String name, FileManagerI fm) {
		HeapSegmentI ts = DefaultHeapSegment.valueOf(name, fm);
		return new DefaultListsSegment(ts);
	}

	@Override
	public long addList(byte[][] content) {
		long next = NULL;
		for (int i = content.length - 1; i >= 0; i--) {
			byte[] content2 = ByteArrayUtil.concate(next, content[i]);
			next = this.target.add(content2);
		}

		return next;
	}

	private long readNextPointer(byte[] contentX) {
		return ByteArrayUtil.readLong(contentX, OFFSET1);
	}

	private long readPrePointer(byte[] contentX) {
		return ByteArrayUtil.readLong(contentX, 0);
	}

	private void writeNextPointer(long nextPointer, byte[] contentX) {

		ByteArrayUtil.writeLong(nextPointer, contentX, OFFSET1);
	}

	private void writePrePointer(long prePointer, byte[] contentX) {
		ByteArrayUtil.writeLong(prePointer, contentX, 0);
	}

	@Override
	public byte[] getElement(long pointer) {

		byte[] content2 = this.target.get(pointer);
		return ByteArrayUtil.subArray(content2, OFFSET1);
	}

	private byte[] contentX(long prePointer, long nextPointer, byte[] content) {
		byte[] pre = ByteArrayUtil.writeLong(prePointer);
		byte[] next = ByteArrayUtil.writeLong(nextPointer);
		return ByteArrayUtil.concate(pre, next, content);

	}

	@Override
	public long addElement(long pointer, byte[] content) {

		byte[] preContentX = this.target.get(pointer);
		long preNextPointer = this.readNextPointer(preContentX);
		byte[] contentX = contentX(pointer, preNextPointer, content);

		long rt = this.target.add(contentX);
		this.writeNextPointer(rt, preContentX);

		long p2 = this.target.update(pointer, preContentX);
		if (p2 != pointer) {
			throw new GsException("pointer changed?");
		}
		return rt;

	}

	@Override
	public long updateElement(long pointer, byte[] content) {
		byte[] contentX = this.target.get(pointer);
		long prePointer = this.readPrePointer(contentX);
		long nextPointer = this.readNextPointer(contentX);
		contentX = this.contentX(prePointer, nextPointer, content);//
		long rt = this.target.update(pointer, contentX);
		if (rt != pointer) {// location updated.
			if (prePointer != NULL) {
				this.updateNextPointer(prePointer, rt);
			}

			if (nextPointer != NULL) {
				this.updatePrePointer(nextPointer, rt);
			}

		}
		return rt;

	}

	private void updatePrePointer(long pointer, long newPrePointer) {
		byte[] contentX = this.target.get(pointer);
		this.writePrePointer(newPrePointer, contentX);
		long p = this.target.update(pointer, contentX);
		if (p != pointer) {
			throw new GsException();
		}
	}

	private void updateNextPointer(long pointer, long newNextPointer) {
		byte[] contentX = this.target.get(pointer);
		this.writeNextPointer(newNextPointer, contentX);
		long p = this.target.update(pointer, contentX);
		if (p != pointer) {
			throw new GsException();
		}
	}

	@Override
	public void removeList(long pointer) {

		byte[] contentX = this.target.get(pointer);
		long prePointer = this.readPrePointer(contentX);
		if (prePointer != NULL) {
			this.updateNextPointer(prePointer, NULL);
		}

		while (pointer != NULL) {

			long next = this.readNextPointer(contentX);
			this.target.remove(pointer);//
			//

			if (next == NULL) {
				break;
			}
			pointer = next;
			contentX = this.target.get(pointer);//
		}
	}

	@Override
	public void dumpInternal(Writer writer) throws IOException {
		// TODO Auto-generated method stub

	}

}
