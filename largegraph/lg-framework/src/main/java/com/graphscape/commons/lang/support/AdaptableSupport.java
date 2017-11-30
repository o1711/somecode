package com.graphscape.commons.lang.support;

import java.util.HashMap;
import java.util.Map;

import com.graphscape.commons.lang.AdaptableI;
import com.graphscape.commons.lang.GsException;

public class AdaptableSupport implements AdaptableI {

	protected Map<Class, Object> adapterObject = new HashMap<Class, Object>();

	@Override
	public <T> T getInterface(Class<T> cls) {
		return this.getInterface(cls, false);

	}

	protected <T> void setAdapterObject(Class<T> cls, T obj) {
		this.adapterObject.put(cls, obj);
	}

	@Override
	public <T> T getInterface(Class<T> cls, boolean force) {

		T rt = (T) this.adapterObject.get(cls);
		if (rt == null && force) {
			throw new GsException("not found adapter interface:" + cls + " for object of cls:" + this.getClass());
		}
		return rt;
	}

}
