/**
 * Dec 14, 2013
 */
package com.graphscape.commons.handling.support;

import java.util.ArrayList;
import java.util.List;

import com.graphscape.commons.handling.HandlingContextI;
import com.graphscape.commons.lang.HandlerI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class CollectionHandler<S, X, T extends HandlingContextI<S, X>> implements HandlerI<T> {
	private List<HandlerI<T>> handlers = new ArrayList<HandlerI<T>>();

	public CollectionHandler(List<HandlerI<T>> hdls) {
		this.handlers.addAll(hdls);
	}

	public CollectionHandler() {
	}

	public int size() {
		return this.handlers.size();
	}

	public void addAll(List<HandlerI<T>> hls) {
		this.handlers.addAll(hls);
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
