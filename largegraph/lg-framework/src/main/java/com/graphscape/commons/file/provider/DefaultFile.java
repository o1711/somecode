/**
 * Jan 4, 2014
 */
package com.graphscape.commons.file.provider;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.RandomAccessContent;
import org.apache.commons.vfs2.util.RandomAccessMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.graphscape.commons.file.ByteCursorI;
import com.graphscape.commons.file.ByteCursorWriter;
import com.graphscape.commons.file.ByteWindowI;
import com.graphscape.commons.file.FileI;
import com.graphscape.commons.file.PlainRegionI;
import com.graphscape.commons.file.RegionI;
import com.graphscape.commons.file.RowRegionI;
import com.graphscape.commons.file.provider.formaters.DefaultByteFormater;
import com.graphscape.commons.file.support.DumpableSupport;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.record.provider.serializer.LongSerializer;
import com.graphscape.commons.record.provider.serializer.ShortSerializer;
import com.graphscape.commons.record.provider.serializer.StringSerializer;
import com.graphscape.commons.util.ObjectUtil;

/**
 * 
 * <code>
 * Segment. 
 * +----------------------------------------------------------------------------------------------------+
 * | type 	| key len	| key 		| tail		| content len	| free len	| content 	| free space	|
 * |----------------------------------------------------------------------------------------------------|
 * | 1 byte	| 2 bytes	| * bytes	| 1 byte	| 8 bytes		| 8 bytes	| * bytes 	| * bytes 		|
 * +----------------------------------------------------------------------------------------------------+
 * 
 * </code>
 * 
 * @author wuzhen0808@gmail.com
 * 
 */
public class DefaultFile extends DumpableSupport implements FileI {

	private static final Logger LOG = LoggerFactory.getLogger(DefaultFile.class);

	public static class Entry extends ByteCursorWriter {

		private static final int TYPE = 0;

		private static final int KEN_LEN = 1;

		private static final int KEN = 2;

		private static final int TAIL = 3;

		private static final int CONTENT_LEN = 4;

		private static final int FREE_LEN = 5;

		private static final int CONTENT = 6;

		private static final int FREE_SAPCE = 7;

		private RandomAccessContent content;

		private PlainRegionI region_cache;

		public Entry(ByteCursorI cur, RandomAccessContent content) {
			super(cur);
			this.content = content;
		}

		public static Entry valueOf(ByteWindowI window, PlainRegionI main, long offset,
				RandomAccessContent content) {
			ByteCursorI cur = window.open(main, offset);

			Entry rt = new Entry(cur, content);
			return rt;
		}

		public static Entry valueOf(ByteWindowI window) {
			ByteCursorI cur = window.open();
			return new Entry(cur, null);//
		}

		public boolean isTemp() {
			return this.content == null;
		}

		public int getContentRelativeOffset() {
			long aoff1 = this.cursor.root().firstChild().getAbsoluteOffset();
			long aoff2 = this.cursor.right(CONTENT).getAbsoluteOffset();
			return (int) (aoff2 - aoff1);
		}

		public Entry write(byte type, String key, int contentSize, long freeSize) {

			return this.writeType(type).writeKey(key).writeTail(false).writeContentLength(contentSize)
					.writeFreeLen(freeSize);

		}

		public Entry writeType(byte type) {
			this.cursor.root().firstChild().writeByte(0, type);
			return this;
		}

		public Entry writeKey(String key) {
			byte[] keyA = key.getBytes();// TODO move to serializer in cursor.
			this.cursor.root().firstChild().right().writeShort(0, (short) keyA.length);
			this.cursor.right().write(0, keyA);//
			return this;
		}

		public long readAbsoluteOffset() {
			return this.cursor.root().getAbsoluteOffset();
		}

		public long readLength() {
			return this.cursor.root().getLength();
		}

		public long readContentLength() {
			return this.cursor.root().firstChild().right(CONTENT_LEN).readLong(0);
		}

		public long readFreeLength() {
			return this.cursor.root().firstChild().right(FREE_LEN).readLong(0);
		}

		public Entry writeContentLength(long len) {
			this.cursor.root().firstChild().right(CONTENT_LEN).writeLong(0, len);
			return this;
		}

		public Entry writeFreeLen(long len) {
			this.cursor.root().firstChild().right(FREE_LEN).writeLong(0, len);
			return this;
		}

		public Entry writeTail(boolean tail) {
			this.cursor.root().firstChild().right(TAIL).writeByte(0, (byte) (tail ? 1 : 0));
			return this;
		}

		public short readKeyLength() {
			return this.cursor.root().firstChild().right().readShort(0);
		}

		public boolean isTail() {
			return (byte) 1 == this.cursor.root().firstChild().right(TAIL).readByte(0);
		}

		public String readKey() {
			byte[] ks = this.cursor.root().firstChild().right(2).read();
			return new String(ks);
		}

		public byte readType() {
			return this.cursor.root().firstChild().readByte(0);
		}

		public boolean isFree() {
			return TYPE_FREE == this.readType();
		}

		public boolean isKey(String k) {
			String key = this.readKey();
			return ObjectUtil.nullSafeEquals(k, key);
		}

		public long readOffset() {
			return this.cursor.root().firstChild().right(CONTENT).getAbsoluteOffset();
		}

		private PlainRegionI getRegion() {
			if (this.region_cache == null) {

				String key = new String(this.readKey());
				long off = this.readOffset();
				long cLen = this.readContentLength();
				long freeLen = this.readFreeLength();
				long capacity = cLen + freeLen;
				int expand = this.isTail() ? 1024 : 0;

				PlainRegionI reg = new DefaultPlainRegion(key, this.content, off, cLen, capacity, expand);

				this.region_cache = reg;
			}
			return this.region_cache;

		}
	}

	private static final byte TYPE_FREE = -1;

	private static final byte TYPE_PLAIN = 0;

	private static final byte TYPE_ROW = 1;

	private static final long NULL = -1;

	private FileObject file;

	private RandomAccessContent content;

	private PlainRegionI main;

	private Map<String, Entry> regionMap;

	private boolean opened;

	private int requiredInitHeaderSpace;

	private static Map<Class, Byte> cls2typeMap = new HashMap<Class, Byte>();
	static {
		cls2typeMap.put(PlainRegionI.class, TYPE_PLAIN);
		cls2typeMap.put(RowRegionI.class, TYPE_ROW);
	}

	private static ByteWindowI WINDOW = new DefaultByteWindow("REGION")//
			.addChild(new DefaultByteWindow("TYPE", 1))//
			.addChild(new DefaultByteWindow("KLEN", 2).serializer(new ShortSerializer()))//
			.addChild(new DefaultByteWindow("KEY", "KLEN").serializer(new StringSerializer()))//
			.addChild(new DefaultByteWindow("TAIL", 1))//
			.addChild(new DefaultByteWindow("CONTENT_LEN", 8).serializer(new LongSerializer()))//
			.addChild(new DefaultByteWindow("FREE_LEN", 8).serializer(new LongSerializer()))//
			.addChild(new DefaultByteWindow("CONTENT", "CONTENT_LEN"))//
			.addChild(new DefaultByteWindow("FREE", "FREE_LEN"))//
	;//

	public DefaultFile(FileObject file, int headerSpace) {
		super();
		this.file = file;
		this.requiredInitHeaderSpace = headerSpace;
	}

	@Override
	public void open() {
		this.doOpen();
	}

	private void doOpen() {
		try {
			this.content = this.file.getContent().getRandomAccessContent(RandomAccessMode.READWRITE);
			long len;
			len = this.content.length();
			this.main = new DefaultPlainRegion("main", this.content, 0L, len, len, 1024);
		} catch (FileSystemException e) {
			throw new GsException(e);
		} catch (IOException e) {
			throw new GsException(e);
		}

		long len = this.getRawLength();
		if (len == 0) {
			this.doInit();
		}
		this.regionMap = new HashMap<String, Entry>();
		long off = 0;
		long entryLen = 0;
		do {
			off += entryLen;
			Entry entry = this.loadEntry(off);
			if (LOG.isDebugEnabled()) {
				LOG.debug("load region,absoluteOffset:" + entry.readAbsoluteOffset() + "key:"
						+ entry.readKey() + ",kenLen:" + entry.readKeyLength() + ",contentLength:"
						+ entry.readContentLength()+",freeLen:"+entry.readFreeLength()+",isTail:"+entry.isTail());
				
			}
			entryLen = entry.readLength();
			if (entry.isTail()) {
				break;
			}

		} while (true);
		// load

		this.opened = true;

	}

	protected Entry loadEntry(long pointer) {
		ByteCursorI cur = WINDOW.open(this.main, pointer);
		Entry entry = new Entry(cur, this.content);
		String key = entry.readKey();

		if (null != this.regionMap.get(key)) {
			throw new GsException("duplicated region:" + key + ",pointer:" + pointer);
		}

		this.regionMap.put(key, entry);

		return entry;
	}

	protected void doInit() {

		// free seg
		Entry en1 = Entry.valueOf(WINDOW, main, 0, content);
		en1.writeType(TYPE_FREE).writeKey("free-region").writeTail(false).writeContentLength(0)
				.writeFreeLen(this.requiredInitHeaderSpace);

		// the tail
		long pointer = en1.readLength();
		Entry en2 = Entry.valueOf(WINDOW, main, pointer, content);
		en2.writeType(TYPE_PLAIN).writeKey("tail-region").writeTail(true).writeContentLength(0)
				.writeFreeLen(0);

	}

	public long getRawLength() {
		try {
			return this.content.length();
		} catch (IOException e) {
			throw new GsException();
		}
	}

	@Override
	public void close() {
		try {
			
		
			for(Entry en:this.regionMap.values()){
				
				PlainRegionI reg = en.region_cache;
				
				if(reg == null){
					continue;
				}
				
				long len = reg.getLength();
				long cap = reg.getCapacity();				
				en.writeContentLength(len);				
				en.writeFreeLen(cap-len);
			}
			this.content.close();
		} catch (IOException e) {
			throw new GsException(e);
		}
		this.content = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.file.FileI#clear()
	 */
	@Override
	public void clear() {
		try {
			this.regionMap.clear();
			this.file.delete();
			this.file.createFile();
			this.doOpen();
		} catch (FileSystemException e) {
			throw new GsException(e);
		}

	}

	@Override
	public PlainRegionI getRegion(String key) {
		this.assertOpened();
		Entry en = this.regionMap.get(key);
		if (en == null) {
			return null;
		}

		return en.getRegion();

	}

	public void assertOpened() {
		if (!this.opened) {
			throw new GsException("please open first.");
		}
	}

	protected Entry getFreeEntry() {
		Entry free = null;
		for (Entry en : this.regionMap.values()) {
			if (en.isFree()) {
				free = en;
				break;
			}
		}
		if (free == null) {
			throw new GsException("no free space");
		}
		return free;
	}

	@Override
	public PlainRegionI addRegion(String key, long size) {
		this.assertOpened();
		if (size <= 0) {
			throw new GsException("size zero/negative:" + size);
		}
		RegionI reg = this.getRegion(key);
		if (reg != null) {
			throw new GsException("duplicated:" + key);
		}

		Entry free = this.getFreeEntry();
		long freeLen = free.readFreeLength();
		// calculate total space required.
		long space = calculateSpace(key, size);

		if (freeLen < size) {
			throw new GsException("no enough free space");
		}

		// ([headers][free ----------------|------------------ content])
		// =>
		// ([headers][free ------ content])|([new headers][new content])
		freeLen = freeLen - space;

		free.writeFreeLen(freeLen);// update len for free

		// the new region's pointer
		long pointer = free.readAbsoluteOffset() + free.readLength();//

		Entry rt = Entry.valueOf(WINDOW, main, pointer, this.content);
		rt.write(type(), key, 0,size);
		this.regionMap.put(rt.readKey(), rt);
		return rt.getRegion();
	}

	private long calculateSpace(String key, long size) {
		return tempEntry(key).getContentRelativeOffset() + size;
	}

	private Entry tempEntry(String key) {
		Entry rt = Entry.valueOf(WINDOW);
		rt.writeType(type()).writeKey(key);
		return rt;
	}

	private byte type() {

		return 0;
	}

	@Override
	public PlainRegionI getTailRegion() {
		Entry rtEn = null;
		for (Entry en : this.regionMap.values()) {
			if (en.isTail()) {
				rtEn = en;
				break;
			}
		}
		return rtEn.getRegion();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.file.FileI#getUnderlying()
	 */
	@Override
	public ByteCursorI getUnderlying() {

		return WINDOW.open(this.main);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.file.FileI#dump(java.io.Writer)
	 */
	@Override
	public void dumpInternal(Writer writer) {
		new DefaultByteFormater().format(true, WINDOW, main, writer);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.file.FileI#delete()
	 */
	@Override
	public void delete() {

		try {
			if (!this.file.delete()) {
				throw new GsException("delete file failure:" + this.file.getName());
			}
		} catch (FileSystemException e) {
			throw new GsException(e);
		}
	}

}
