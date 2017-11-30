package com.graphscape.commons.concurrent.support;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import com.graphscape.commons.lang.Wrapper;

public class ProxyBlockingQueue<T> extends Wrapper<BlockingQueue<T>> implements BlockingQueue<T> {

	public ProxyBlockingQueue(BlockingQueue<T> t) {
		super(t);
	}

	@Override
	public T remove() {
		return this.target.remove();
	}

	@Override
	public T poll() {
		// TODO Auto-generated method stub
		return this.target.poll();
	}

	@Override
	public T element() {
		// TODO Auto-generated method stub
		return this.target.element();
	}

	@Override
	public T peek() {
		// TODO Auto-generated method stub
		return this.target.peek();
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return this.target.size();
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return this.target.isEmpty();
	}

	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return this.target.iterator();
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return this.target.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return this.target.toArray(a);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return this.target.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		// TODO Auto-generated method stub
		return this.target.addAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return this.target.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return this.target.retainAll(c);
	}

	@Override
	public void clear() {
		this.target.clear();
	}

	@Override
	public boolean add(T e) {
		// TODO Auto-generated method stub
		return this.target.add(e);
	}

	@Override
	public boolean offer(T e) {
		// TODO Auto-generated method stub
		return this.target.offer(e);

	}

	@Override
	public void put(T e) throws InterruptedException {
		this.target.put(e);
	}

	@Override
	public boolean offer(T e, long timeout, TimeUnit unit) throws InterruptedException {
		// TODO Auto-generated method stub
		return this.target.offer(e, timeout, unit);
	}

	@Override
	public T take() throws InterruptedException {
		// TODO Auto-generated method stub
		return this.target.take();
	}

	@Override
	public T poll(long timeout, TimeUnit unit) throws InterruptedException {
		// TODO Auto-generated method stub
		return this.target.poll(timeout, unit);
	}

	@Override
	public int remainingCapacity() {
		// TODO Auto-generated method stub
		return this.target.remainingCapacity();
	}

	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return this.target.remove(o);
	}

	@Override
	public boolean contains(Object o) {
		// TODO Auto-generated method stub
		return this.target.contains(o);
	}

	@Override
	public int drainTo(Collection<? super T> c) {
		// TODO Auto-generated method stub
		return this.target.drainTo(c);
	}

	@Override
	public int drainTo(Collection<? super T> c, int maxElements) {
		// TODO Auto-generated method stub
		return this.target.drainTo(c, maxElements);
	}

}
