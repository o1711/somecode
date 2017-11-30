/**
 * Jan 5, 2014
 */
package com.graphscape.commons.record.provider.storage;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.graphscape.commons.debug.support.ProfileAwareSupport;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.record.Position;
import com.graphscape.commons.record.RecordI;
import com.graphscape.commons.record.RecordType;
import com.graphscape.commons.record.StorageI;
import com.graphscape.commons.record.TxListenerI;
import com.graphscape.commons.record.TxStorageI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class DefaultTxStorage extends ProfileAwareSupport implements TxStorageI {

	private static final String PROFILE_COMMIT = DefaultTxStorage.class.getName() + ".commit";

	private static final String PROFILE_ADDRECORD = DefaultTxStorage.class.getName() + ".addRecord";

	private static final int UNKNOW = 0;
	private static final int OPEN = 1;
	private static final int COMMITING = 2;
	private static final int CLOSE = 3;

	protected StorageI storage;

	protected DefaultTxStorageManager xstorage;

	protected int status = 0;

	protected TxOperations operations = new TxOperations();

	protected long nextPosition = -1;

	protected TxListenerI listener;

	protected Lock commitLock;

	protected Lock statusLock;

	public DefaultTxStorage(DefaultTxStorageManager xstorage, Lock commitLock, TxListenerI lis) {
		this.xstorage = xstorage;
		this.storage = xstorage.getTarget();
		this.listener = lis;
		this.commitLock = commitLock;
		this.statusLock = new ReentrantLock();
	}

	@Override
	public void open() {
		this.statusLock.lock();
		try {
			if (this.status != UNKNOW) {
				throw new GsException("illegal status:" + this.status);
			}
			this.status = OPEN;
		} finally {
			this.statusLock.unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.record.TransactionI#commit()
	 */
	@Override
	public void commit() {
		this.beforeProfile(PROFILE_COMMIT);
		try {

			this.statusLock.lock();
			try {
				if (this.status != OPEN) {
					throw new GsException("illegal status:" + this.status);
				}
				this.status = COMMITING;

				this.commitLock.lock();
				try {
					this.doCommit();
				} finally {
					this.commitLock.unlock();
				}

				this.close();
			} finally {
				this.statusLock.unlock();
			}
		} finally {
			this.afterProfile();
		}

	}

	private void doCommit() {

		Map<Position, Position> oldToNewPosition = new HashMap<Position, Position>();

		for (Iterator<TxOperation> it = this.operations.iterator(); it.hasNext();) {
			TxOperation op = it.next();

			Position newPos = null;

			switch (op.getType()) {
			case TxOperation.LOAD:// do nothing.
				break;
			case TxOperation.UPDATE:
				Position oldP = op.getPosition();
				if (oldP.isTemprary()) {// add it.
					newPos = this.storage.addRecord(op.getRecordType(), op.getContent());
				} else {
					this.storage.updateRecord(op.getPosition(), op.getRecordType(), op.getContent());
				}
				break;
			case TxOperation.ADD:
				newPos = this.storage.addRecord(op.getRecordType(), op.getContent());
				break;
			case TxOperation.REMOVE:

			default:
			}
			if (newPos != null) {//
				oldToNewPosition.put(op.getPosition(), newPos);//
			}
		}
		if (this.listener != null) {

			for (Map.Entry<Position, Position> en : oldToNewPosition.entrySet()) {
				Position oldPos = en.getKey();
				Position newPos = en.getValue();
				this.listener.onRecordPositionChanged(oldPos, newPos);
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.record.TransactionI#rollback()
	 */
	@Override
	public void rollback() {
		this.statusLock.lock();
		try {

			if (this.status != OPEN) {
				throw new GsException("illegal status:" + this.status);
			}
			// as if nothing happened.
			this.operations.clear();

			this.close();
		} finally {
			this.statusLock.unlock();
		}
	}

	private void assertStatus(int status) {
		if (this.status != status) {
			throw new GsException("not in the expected status:" + status + ",actual:" + this.status);
		}
	}

	public RecordI getRecord(Position position) {
		this.statusLock.tryLock();
		try {

			this.assertStatus(OPEN);//
			TxOperation op = this.operations.get(position);
			RecordI rt = null;
			if (op == null) {// no operation,create a LOAD operation then.
				rt = this.storage.getRecord(position);
				if (rt == null) {// not found
					return null;
				}
				op = TxOperation.valueOf(position, TxOperation.LOAD, rt.getType(), rt.getContent());
				this.operations.put(position, op);
			} else {// has operation for this record.
				switch (op.getType()) {
				case TxOperation.LOAD:
				case TxOperation.UPDATE:
				case TxOperation.ADD:
					rt = new TxRecord(op.getRecordType(), op.getContent());//
					break;
				case TxOperation.REMOVE:

				default:

				}
			}

			return rt;
		} finally {
			this.statusLock.unlock();
		}
	}

	public Position nextPosition() {
		return new Position(this.nextPosition--);
	}

	public Position addRecord(RecordType type, byte[] content) {
		this.beforeProfile(PROFILE_ADDRECORD);
		try {

			// add a new record, so new id.
			this.statusLock.tryLock();
			try {
				this.assertStatus(OPEN);//
				Position rt = this.nextPosition();//

				TxOperation op = TxOperation.valueOf(rt, TxOperation.ADD, type, content);
				this.operations.put(rt, op);
				return rt;
			} finally {
				this.statusLock.unlock();
			}
		} finally {
			this.afterProfile();
		}
	}

	public void updateRecord(Position position, RecordType type, byte[] content) {
		this.statusLock.tryLock();
		try {
			this.assertStatus(OPEN);//
			TxOperation op = this.operations.get(position);
			if (op == null) {// no operation for this position,so create one.
				op = TxOperation.valueOf(position, TxOperation.UPDATE, type, content);
				this.operations.put(position, op);

			} else {// has operation already for this position.
				switch (op.getType()) {
				case TxOperation.LOAD:
				case TxOperation.UPDATE:
				case TxOperation.ADD:
					op.update(TxOperation.UPDATE, type, content);
					break;
				case TxOperation.REMOVE:
					throw new GsException("no this record position:" + position);
				default:

				}
			}
		} finally {
			this.statusLock.unlock();
		}
	}

	public boolean removeRecord(Position position) {
		this.statusLock.tryLock();
		try {
			this.assertStatus(OPEN);//
			TxOperation op = this.operations.get(position);
			if (op == null) {// no operation for this position,so create one.
				op = TxOperation.valueOf(position, TxOperation.REMOVE, null, null);
				this.operations.put(position, op);

			} else {// has operation already for this position.
				switch (op.getType()) {
				case TxOperation.LOAD:
				case TxOperation.UPDATE:
				case TxOperation.ADD:
					op.update(TxOperation.REMOVE, null, null);
					break;
				case TxOperation.REMOVE:
					throw new GsException("no this record position:" + position);
				default:

				}
			}

			return true;
		} finally {
			this.statusLock.unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.record.ResourceI#close()
	 */
	@Override
	public void close() {
		this.statusLock.tryLock();
		try {
			this.status = CLOSE;
		} finally {
			this.statusLock.unlock();
		}
	}

}
