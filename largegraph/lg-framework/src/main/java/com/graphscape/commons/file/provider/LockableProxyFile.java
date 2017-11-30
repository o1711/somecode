/**
 * Jan 4, 2014
 */
package com.graphscape.commons.file.provider;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.graphscape.commons.file.FileI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class LockableProxyFile extends ProxyPlainFile {
	protected ReadWriteLock lock = new ReentrantReadWriteLock();

	public LockableProxyFile(FileI t) {
		super(t);
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

}