/**
 * Jan 4, 2014
 */
package com.graphscape.commons.file.provider;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class LockableProxyRowFile extends ProxyRowFile{
	protected ReadWriteLock lock = new ReentrantReadWriteLock();

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