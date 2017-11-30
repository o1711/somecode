package org.ta.common.handler;

import java.util.ArrayList;
import java.util.List;

public class TaHandlers<T> implements TaHandler<T> {

	private List<TaHandler<T>> handlerList = new ArrayList<TaHandler<T>>();

	@Override
	public void handle(T t) {
		for (TaHandler<T> h : this.handlerList) {
			h.handle(t);//
		}
	}

	public boolean remove(TaHandler<T> h) {
		return this.handlerList.remove(h);
	}

	public void add(TaHandler<T> h) {
		this.handlerList.add(h);//
	}
}
