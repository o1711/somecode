package com.graphscape.commons.lang.support;

import com.graphscape.commons.lang.WithIdI;
import com.graphscape.commons.lang.Wrapper;

public class DefaultWithId<T> extends Wrapper<T> implements WithIdI<T> {

	protected String id;

	public DefaultWithId(String id, T target) {
		super(target);
		this.id = id;
	}

	@Override
	public String getId() {

		return id;
	}

}
