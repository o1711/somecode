/**
 * Jan 26, 2014
 */
package com.graphscape.commons.probject.provider;

import java.util.HashMap;
import java.util.Map;

import com.graphscape.commons.debug.support.ProfileAwareSupport;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.lang.Wrapper;
import com.graphscape.commons.record.IndexI;
import com.graphscape.commons.record.Position;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class IndexOperations<K> extends ProfileAwareSupport {

	private static final String PROFILE_PUT = IndexOperations.class.getName() + ".put";
	private static final String PROFILE_GET = IndexOperations.class.getName() + ".get";
	private static final String PROFILE_COMMIT = IndexOperations.class.getName() + ".commit";
	private static final String PROFILE_REMOVE = IndexOperations.class.getName() + ".remove";

	private IndexI<K, Position> index;

	private Map<K, IndexOperation<K>> operationMap;

	private Map<Position, Wrapper<Position>> positionMap;

	/**
	 * @param index2
	 */
	public IndexOperations(IndexI<K, Position> index) {
		this.index = index;
		this.applyProfile(index);
		this.positionMap = new HashMap<Position, Wrapper<Position>>();
		this.operationMap = new HashMap<K, IndexOperation<K>>();
	}

	public void put(K k, Position v) {
		this.beforeProfile(PROFILE_PUT);
		try {

			Wrapper<Position> pw = this.getOrCreatePosition(v);//

			IndexOperation<K> op = this.operationMap.get(k);
			if (op == null) {
				op = IndexOperation.put(k, pw);
				this.operationMap.put(k, op);
			} else {
				Wrapper<Position> old = op.getValue();// TODO release?
				op.update(IndexOperation.PUT, pw);//
			}
		} finally {
			this.afterProfile();
		}

	}

	public void remove(K k) {
		this.beforeProfile(PROFILE_REMOVE);
		try {

			IndexOperation<K> op = this.operationMap.get(k);
			if (op == null) {
				op = IndexOperation.remove(k);
			} else {
				op.update(IndexOperation.REMOVE, null);//
			}
		} finally {
			this.afterProfile();
		}
	}

	public void commit() {
		this.beforeProfile(PROFILE_COMMIT);
		try {

			for (IndexOperation<K> op : this.operationMap.values()) {
				byte type = op.getType();
				switch (type) {
				case IndexOperation.PUT:
					this.index.put(op.getKey(), op.getValue().getTarget());//
					break;
				case IndexOperation.REMOVE:
					this.index.remove(op.getKey());//
					break;
				case IndexOperation.LOAD:
					// do nothing.
					break;
				default:
					throw new GsException("no this type:" + type);
				}
			}
		} finally {
			this.afterProfile();
		}

	}

	public IndexOperation<K> getOperation(K k) {
		IndexOperation<K> op = this.operationMap.get(k);
		return op;
	}

	public Wrapper<Position> getOrCreatePosition(Position pos) {
		Wrapper<Position> rt = this.positionMap.get(pos);
		if (rt == null) {
			rt = new Wrapper<Position>(pos);
			this.positionMap.put(pos, rt);//
		}
		return rt;
	}

	public Position get(K k) {
		this.beforeProfile(PROFILE_GET);
		try {

			IndexOperation<K> op = this.operationMap.get(k);
			Position rt = null;
			if (op == null) {
				// do load from index.
				rt = this.index.get(k);
				Wrapper<Position> pw = this.getOrCreatePosition(rt);//
				op = IndexOperation.load(k, pw);
				this.operationMap.put(k, op);//

			} else {
				switch (op.getType()) {
				case IndexOperation.LOAD:
				case IndexOperation.PUT:
					rt = op.getValue().getTarget();
					break;
				case IndexOperation.REMOVE:
					rt = null;
				}

			}
			return rt;
		} finally {
			this.afterProfile();
		}
	}

	public void positionUpdate(Position oldPos, Position newPos) {
		Wrapper<Position> pw = this.positionMap.remove(oldPos);

		if (pw == null) {
			return;
		}
		pw.setTarget(newPos);//
		this.positionMap.put(newPos, pw);//
	}

}
