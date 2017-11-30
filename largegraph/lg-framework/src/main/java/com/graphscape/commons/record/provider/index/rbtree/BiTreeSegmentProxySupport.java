/**
 * Jan 29, 2014
 */
package com.graphscape.commons.record.provider.index.rbtree;

import java.io.IOException;
import java.io.Writer;

import com.graphscape.commons.cache.CacheI;
import com.graphscape.commons.cache.provider.DefaultCache;
import com.graphscape.commons.file.support.DumpableSupport;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.record.BiTreeSegmentI;
import com.graphscape.commons.util.ByteArrayUtil;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public abstract class BiTreeSegmentProxySupport<E extends BiTreeSegmentProxySupport.Entry> extends
		DumpableSupport {

	public static class Direct {

		public static final Direct ANY = new Direct((byte) 0, "ANY");

		public static final Direct LEFT = new Direct((byte) -1, "LEFT");

		public static final Direct RIGHT = new Direct((byte) 1, "RIGHT");

		private byte value;

		private String name;

		private Direct(byte v, String name) {
			this.value = v;
			this.name = name;
		}

		public Direct opposite() {
			return valueOf((byte) (0 - (int) this.value));
		}

		public static Direct valueOf(byte value) {
			switch (value) {
			case -1:
				return LEFT;
			case 1:
				return RIGHT;
			case 0:
				return ANY;
			default:
				throw new GsException("no this direct:" + value);
			}
		}

		@Override
		public int hashCode() {
			return this.value;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null || !(obj instanceof Direct)) {
				return false;
			}
			return this.value == ((Direct) obj).value;
		}

		@Override
		public String toString() {
			return this.name;
		}
	}

	public static abstract class Entry {

		protected long pointer;
		private byte[] content;
		private byte[] userHeader;

		BiTreeSegmentProxySupport tree;

		protected int userHeaderLength;

		protected Direct direct;

		public void initialize(long pointer, Direct dir, int entryHeaderLength, BiTreeSegmentI segment,
				BiTreeSegmentProxySupport tr) {
			tree = tr;
			this.direct = dir;
			this.userHeaderLength = entryHeaderLength;
			this.pointer = pointer;
		}

		public byte[] getTheContent() {
			if (this.content == null) {
				this.content = this.tree.getContent(pointer);
			}
			return this.content;
		}
		
		public byte[] getUserHeader(){
			if (this.userHeader == null) {
				this.userHeader = this.tree.getUserHeader(pointer);
			}
			return this.userHeader;
		}
		public void writeUserHeader(byte[] userHeader) {
			this.userHeader = userHeader;
			this.tree.updateUserHeader(pointer, userHeader);
			
		}

		public Direct getDirect() {
			if (this.direct == null) {
				Entry p = this.parent();
				if (p == null) {
					this.direct = Direct.ANY;
				} else {
					Entry l = p.left();

					this.direct = (l != null && l.pointer == this.pointer ? Direct.LEFT : Direct.RIGHT);
				}
			}

			return this.direct;
		}

		public boolean isDirect(Direct dir) {
			return dir.equals(this.getDirect());
		}

		public boolean isLeft() {
			return isDirect(Direct.LEFT);
		}

		public boolean isRight() {
			return isDirect(Direct.RIGHT);
		}

		public boolean isLeftOf(Entry parent) {

			Entry l = parent.left();
			return l != null && l.pointer == this.pointer;//

		}

		public boolean isRightOf(Entry parent) {
			Entry l = parent.right();
			return l != null && l.pointer == this.pointer;//

		}

		public <E extends Entry> E grand() {
			E p = parent();
			return p.parent();
		}

		public <E extends Entry> E parent() {
			return (E) tree.getParent(pointer);
		}

		public <E extends Entry> E left() {
			return (E) tree.getLeft(pointer);
		}

		public <E extends Entry> E right() {
			return (E) tree.getRight(pointer);
		}

		public <E extends Entry> E leftMostChild() {
			E pre = (E) this;
			E next = null;
			while (true) {
				next = pre.left();
				if (next == null) {
					break;
				}
				pre = next;
			}
			return pre;
		}

	}

	private static final String PROFILE_GETENTRY = BiTreeSegmentProxySupport.class.getName() + ".getEntry";

	private static final String PROFILE_DOGETENTRY = BiTreeSegmentProxySupport.class.getName()
			+ ".doGetEntry";

	public static final long NULL = -1;

	protected int userHeaderLength;

	private BiTreeSegmentI segment;

	private CacheI<Long> parentCache = new DefaultCache<Long>();
	private CacheI<Long> leftCache = new DefaultCache<Long>();
	private CacheI<Long> rightCache = new DefaultCache<Long>();
	private CacheI<E> entryCache = new DefaultCache<E>();
	private boolean useEntryCache = true;

	private Long cache_rootPointer;

	public BiTreeSegmentProxySupport(BiTreeSegmentI seg, int entryHeaderLength) {
		this.segment = seg;
		this.userHeaderLength = entryHeaderLength;
		this.applyProfile(this.segment);
		this.applyProfile(this.parentCache);
		this.applyProfile(this.leftCache);
		this.applyProfile(this.rightCache);
		this.applyProfile(this.entryCache);

	}

	protected abstract E newEntry();

	public void open() {
		this.segment.open();
	}

	protected long addLeft(long pointer, byte[] header, byte[] content) {
		long rt = this.segment.addLeft(pointer, header, content);//
		leftCache.clear(pointer);//
		return rt;

	}

	protected long addRight(long pointer, byte[] header, byte[] content) {
		rightCache.clear(pointer);
		return this.segment.addRight(pointer, header, content);//

	}

	protected long addRoot(byte[] header,byte[] content) {
		//
		this.cache_rootPointer = null;
		return this.segment.addRoot(header,content);
	}

	public void close() {
		this.segment.close();
	}

	protected E getRoot() {

		if (this.cache_rootPointer == null) {
			this.cache_rootPointer = this.segment.getRootPointer();
		}

		long root = this.cache_rootPointer;

		if (root == NULL) {
			return null;
		}

		return getEntry(root, null);
	}

	protected E getEntry(long pointer) {
		return this.getEntry(pointer, null);//
	}

	protected E getEntry(long pointer, Direct dir) {
		this.beforeProfile(PROFILE_GETENTRY);
		try {
			if (this.useEntryCache) {

				E rt = this.entryCache.get(pointer);
				if (rt == null) {
					rt = this.doGetEntry(pointer, dir);
					this.entryCache.put(pointer, rt);//
				}
				return rt;
			} else {
				return this.doGetEntry(pointer, dir);

			}
		} finally {
			this.afterProfile();
		}

	}

	private E doGetEntry(long pointer, Direct dir) {
		this.beforeProfile(PROFILE_DOGETENTRY);
		try {

			if (pointer == NULL) {
				return null;
			}

			E e = this.newEntry();
			e.initialize(pointer, dir, this.userHeaderLength, this.segment, this);
			return e;
		} finally {
			this.afterProfile();
		}
	}

	private byte[] getUserHeader(long pointer){
		return this.segment.getUserHeader(pointer);//
	}
	private byte[] getContent(long pointer) {
		byte[] rt = this.segment.get(pointer);//
		return rt;
	}

	protected E getLeft(long pointer) {
		Long l = this.leftCache.get(pointer);
		if (l == null) {
			l = this.segment.getLeftPointer(pointer);
			this.leftCache.put(pointer, l);
		}

		E rt = this.getEntry(l, Direct.LEFT);
		return rt;

	}

	protected E getRight(long pointer) {
		Long l = this.rightCache.get(pointer);
		if (l == null) {
			l = this.segment.getRightPointer(pointer);
			this.rightCache.put(pointer, l);
		}

		E rt = this.getEntry(l, Direct.RIGHT);
		return rt;

	}

	protected E getParent(long pointer) {
		Long l = this.parentCache.get(pointer);
		if (l == null) {
			l = this.segment.getParentPointer(pointer);
			this.parentCache.put(pointer, l);
		}
		return this.getEntry(l, null);
	}

	protected long updateUserHeader(long pointer, byte[] header) {
		this.entryCache.clear(pointer);
		return this.segment.updateUserHeader(pointer, header);//
	}
	
	protected long updateContent(long pointer, byte[] content){
		this.entryCache.clear(pointer);
		return this.segment.update(pointer, content);
	}

	protected void setRootPointer(long pointer) {
		this.segment.setRootPointer(pointer);
		this.cache_rootPointer = pointer;
	}

	protected void remove(long pointer) {
		this.entryCache.clear(pointer);
		this.segment.remove(pointer);//
		if (this.cache_rootPointer != null) {
			if (pointer == this.cache_rootPointer) {
				this.cache_rootPointer = null;
			}
		}
	}

	protected long removeParentLink(long pointer) {
		long rt = this.segment.removeParentLink(pointer);
		this.parentCache.clear(pointer);//
		this.rightCache.clear(rt);
		this.leftCache.clear(rt);
		this.entryCache.clear(pointer, rt);//

		return rt;
	}

	protected long updateLeftLink(long pointer, long childPointer) {

		long rt = this.segment.updateLeftLink(pointer, childPointer);
		this.leftCache.clear(pointer);
		this.parentCache.clear(childPointer);//
		this.parentCache.clear(rt);//
		// TODO only need clear direct
		this.entryCache.clear(pointer, childPointer, rt);//

		return rt;
	}

	protected long updateRightLink(long pointer, long childPointer) {
		long rt = this.segment.updateRightLink(pointer, childPointer);
		this.rightCache.clear(pointer);
		this.parentCache.clear(childPointer);//
		this.parentCache.clear(rt);//
		this.entryCache.clear(pointer, childPointer, rt);//

		return rt;
	}

	@Override
	public void dumpInternal(Writer writer) throws IOException {
		this.segment.dump();
	}

	public void clear() {
		this.segment.clear();
	}

}
