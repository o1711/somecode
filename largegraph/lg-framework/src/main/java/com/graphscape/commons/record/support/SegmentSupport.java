/**
 * Jan 4, 2014
 */
package com.graphscape.commons.record.support;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.graphscape.commons.debug.TodoException;
import com.graphscape.commons.file.HasRegionI;
import com.graphscape.commons.file.PlainRegionI;
import com.graphscape.commons.file.support.ProxyBinaryHeader;
import com.graphscape.commons.lang.CallbackI;
import com.graphscape.commons.record.SegmentI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public abstract class SegmentSupport<T extends HasRegionI> extends ProxyBinaryHeader<T> implements SegmentI {

	protected ReadWriteLock lock = new ReentrantReadWriteLock();

	public SegmentSupport(T header) {
		super(header);
	}

	public void writeLock() {
		this.lock.writeLock().lock();
	}

	public void writeUnlock() {
		this.lock.writeLock().unlock();
	}

	public void readLock() {
		this.lock.readLock().lock();
	}

	public void readUnlock() {
		this.lock.readLock().unlock();
	}

	@Override
	public void open() {

	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

}
