/**
 * Jan 26, 2014
 */
package com.graphscape.commons.probject.provider;

import com.graphscape.commons.lang.Wrapper;
import com.graphscape.commons.record.Position;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class IndexOperation<K> {

	public static final byte LOAD = 0;
	public static final byte PUT = 1;
	public static final byte REMOVE = 2;

	private byte type;
	private K key;
	private Wrapper<Position> value;

	public IndexOperation(byte type, K k, Wrapper<Position> v) {
		this.type = type;
		this.key = k;
		this.value = v;
	}

	public byte getType() {
		return this.type;
	}

	public void update(byte type,Wrapper<Position> pw) {
		this.type = type;
		this.value = pw;
	}

	public static <K> IndexOperation<K> load(K k, Wrapper<Position> v) {
		return new IndexOperation<K>(LOAD, k, v);
	}

	public static <K> IndexOperation<K> put(K k, Wrapper<Position> v) {
		return new IndexOperation<K>(PUT, k, v);
	}

	public static <K> IndexOperation<K> remove(K k) {
		return new IndexOperation<K>(REMOVE, k, null);
	}

	public K getKey() {
		return key;
	}

	public Wrapper<Position> getValue() {
		return value;
	}
}
