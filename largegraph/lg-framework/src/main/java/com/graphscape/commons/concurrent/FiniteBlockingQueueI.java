package com.graphscape.commons.concurrent;

import com.graphscape.commons.util.TimeAndUnit;

public interface FiniteBlockingQueueI<T> {

	public void put(T t);

	public boolean isClosed();

	public void close();

	/**
	 * @param timeout
	 * @return
	 * @throws NoSuchElementException
	 *             after finish is called and the queue is empty.
	 */
	public T poll(TimeAndUnit timeout);

	/**
	 * @return
	 * @throws NoSuchElementException
	 *             after finish is called and the queue is empty.
	 * 
	 */
	public T take();

}
