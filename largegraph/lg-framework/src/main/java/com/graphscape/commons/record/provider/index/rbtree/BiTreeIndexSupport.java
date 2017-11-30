package com.graphscape.commons.record.provider.index.rbtree;

import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.graphscape.commons.file.PlainRegionI;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.record.BiTreeSegmentI;
import com.graphscape.commons.record.IndexI;
import com.graphscape.commons.record.SerializerI;
import com.graphscape.commons.util.ByteArrayUtil;

/**
 * 
 * <code>
 * 
 * +--------------------------------------------------------+ 
 * | header | key length 	| key 	| value length 	| value |
 * |--------------------------------------------------------| 
 * | 1X? 	| 4 bytes 		| * 	| 4 bytes 		| * 	| 
 * +--------------------------------------------------------+
 * 
 * </code>
 * 
 * @author wuzhen0808@gmail.com
 */
public abstract class BiTreeIndexSupport<K, V, E extends BiTreeIndexSupport.Entry<K, V>> extends BiTreeSegmentProxySupport<E> implements
		IndexI<K, V> {
	private static final Logger LOG = LoggerFactory.getLogger(BiTreeIndexSupport.class);
	private static final String PROFILE_PUT = BiTreeIndexSupport.class.getName() + ".put";
	private static final String PROFILE_GET = BiTreeIndexSupport.class.getName() + ".get";
	
	private static final String PROFILE_REMOVE = BiTreeIndexSupport.class.getName() + ".remove";

	
	public static abstract class Entry<K, V> extends BiTreeSegmentProxySupport.Entry{

		private K key;
		private V value;

		protected BiTreeIndexSupport<K, V, ?> index;
		
		@Override
		public void initialize(long pointer, Direct dir, int entryHeaderLength,
				BiTreeSegmentI segment, BiTreeSegmentProxySupport tr) {
			super.initialize(pointer, dir, entryHeaderLength, segment, tr);
			this.index = (BiTreeIndexSupport<K, V, ?>)tr;
		}

		public V getValue() {
			if (this.value == null) {

				this.value = this.deserializeValue(valueOffset(), valueLength());
			}
			return this.value;
		}

		public K getKey() {
			if (this.key == null) {
				int keyLength = ByteArrayUtil.readInt(getTheContent(), 0);
				this.key = this.deserializeKey(keyOffset(), keyLength);
			}
			return this.key;
		}

		protected V deserializeValue(int offset, int len) {
			byte[] v = ByteArrayUtil.subArray(this.getTheContent(), offset, len);
			return (V) index.valueMarshaller.deserialize(v);
		}

		protected K deserializeKey(int offset, int len) {
			// ByteArrayUtil.subArray(b, offset, len);
			byte[] key = ByteArrayUtil.subArray(this.getTheContent(), offset, len);

			return (K) index.keyMarshaller.deserialize(key);
		}

		protected int keyOffset() {
			return 0 + HED2;
		}

		protected int keyLength() {
			return ByteArrayUtil.readInt(getTheContent(), 0);
		}

		protected int valueLength() {
			int vlenpos = keyOffset() + keyLength();

			int rt = ByteArrayUtil.readInt(getTheContent(), vlenpos);

			return rt;

		}

		protected int valueOffset() {
			return 0 + HED2 + keyLength() + HED4;
		}

		@Override
		public String toString() {
			return "" + this.getKey();
		}

	}

	public static final int HED2 = 4;

	public static final int HED4 = 4;

	public static final int LONG = 8;

	public static final long NULL = -1;

	//private BiTreeSegmentI segment;

	protected Comparator<K> comparator;

	protected SerializerI<K> keyMarshaller;

	protected SerializerI<V> valueMarshaller;

	public BiTreeIndexSupport(int entryHeaderLength, Comparator<K> comparator, SerializerI<K> marshaller,
			SerializerI<V> valueMarshaller, BiTreeSegmentI seg) {
		super(seg,entryHeaderLength);
		
		this.comparator = comparator;
		this.keyMarshaller = marshaller;
		this.valueMarshaller = valueMarshaller;
		
	}

	@Override
	public void open() {
		super.open();
	}

	@Override
	public void close() {
		super.close();
	}

	@Override
	public V put(K k, V v) {
		this.beforeProfile(PROFILE_PUT);
		try {
			return this.doPut(k, v);
		} finally {
			this.afterProfile();
		}
	}

	private V doPut(K k, V v) {

		E en = this.getRoot();
		if (en == null) {// root node.
			byte[] content = this.buildContent(k, v);
			byte[] header = this.buildRootHeader();
			long root = this.addRoot(header,content);
			this.afterRootAdded(getEntry(root));
			return v;
		}
		// else: find the parent
		E parent = null;
		int comp = 0;
		do {
			parent = en;
			comp = this.comparator.compare(k, en.getKey());
			if (comp < 0) {// to left.
				en = en.left();
			} else if (comp > 0) {
				en = en.right();
			} else {// found the node.
				return setValue(en, v);
			}
		} while (en != null);

		if (comp < 0) {
			byte[] content = buildContent( k, v);
			byte[] header = buildLeftHeader();
			long p = this.addLeft(parent.pointer, header,content);
			this.afterLeftAdded(parent, getEntry(p, Direct.LEFT));
		} else {
			byte[] content = buildContent(k, v);
			byte[] header = this.buildRightHeader();
			long p = this.addRight(parent.pointer,header, content);
			this.afterRightAdded(parent, getEntry(p, Direct.RIGHT));

		}

		return null;
	}
	
	protected abstract byte[] buildLeftHeader();
	
	protected abstract byte[] buildRightHeader();

	protected abstract byte[] buildRootHeader();

	protected byte[] buildContent(K k, V v) {
		
		byte[] vcontent = this.serializeValue(v);
		byte[] kcontent = this.serializeKey(k);// TODO use cache.
		byte[] klen = ByteArrayUtil.writeInt(kcontent.length);
		byte[] vlen = ByteArrayUtil.writeInt(vcontent.length);

		byte[] rt = ByteArrayUtil.concate(klen, kcontent, vlen, vcontent);

		return rt;
	}

	protected abstract void afterRootAdded(E en);

	protected abstract void afterLeftAdded(E parent, E en);

	protected abstract void afterRightAdded(E parent, E en);

	protected E getEntry(K k) {
		E en = this.getRoot();
		int comp;
		while (en != null) {
			comp = this.comparator.compare(k, en.getKey());
			if (comp < 0) {// to left.
				en = en.left();
			} else if (comp > 0) {
				en = en.right();
			} else {// found the node.
				return en;
			}
		}
		;

		return null;
	}

	@Override
	public V remove(K k) {
		this.beforeProfile(PROFILE_REMOVE);
		try {
			return this.doRemove(k);

		} finally {
			this.afterProfile();
		}
	}

	private V doRemove(K k) {

		E en = this.getEntry(k);
		if (en == null) {
			return null;
		}
		V rt = en.getValue();
		this.deleteEntry(en);
		return rt;
	}

	protected abstract void deleteEntry(E en);

	protected V setValue(E en, V v) {
		V rt = en.getValue();// old value
		byte[] content = buildContent(en.getKey(), v);
		this.updateContent(en.pointer, content);//
		return rt;
	}

	protected byte[] serializeKey(K k) {
		return this.keyMarshaller.serialize(k);
	}

	protected byte[] serializeValue(V v) {
		return this.valueMarshaller.serialize(v);
	}

	@Override
	public V get(K k) {
		this.beforeProfile(PROFILE_GET);
		try {

			E en = this.getEntry(k);
			return en == null ? null : en.getValue();
		} finally {
			this.afterProfile();
		}
	}
	
	@Override
	public void clear() {
		super.clear();
	}

}
