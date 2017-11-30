/**
 * Dec 14, 2013
 */
package com.graphscape.commons.handling.support;

import java.util.ArrayList;
import java.util.List;

import com.graphscape.commons.lang.HandlerI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class CollectionListener<T> implements HandlerI<T> {
	private List<HandlerI<T>> handlers;

	public CollectionListener() {
		this.handlers = new ArrayList<HandlerI<T>>();
	}

	public int size() {
		return this.handlers.size();
	}

	public void addHandler(HandlerI<T> h) {
		this.handlers.add(h);
	}

	@Override
	public void handle(T sc) {
		for (HandlerI<T> h : this.handlers) {
			h.handle(sc);
		}
	}

}
