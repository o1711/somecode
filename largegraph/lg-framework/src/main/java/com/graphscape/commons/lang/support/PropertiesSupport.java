/**
 */
package com.graphscape.commons.lang.support;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.lang.PropertiesI;
import com.graphscape.commons.lang.provider.default_.DefaultProperties;
import com.graphscape.commons.util.ObjectUtil;

/**
 * @author wu
 * 
 */
public abstract class PropertiesSupport<T> implements PropertiesI<T> {
	/* */
	@Override
	public void setPropertiesByArray(Object... keyValues) {
		int i = 0;
		while (i < keyValues.length) {
			String key = String.valueOf(keyValues[i++]);
			Object value = keyValues[i++];
			T t = (T) value;
			this.setProperty(key, t);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hp.topology.commons.api.PropertiesI#getProperty(java.lang
	 * .String, java.lang.Object)
	 */
	@Override
	public <X extends T> X getProperty(String key, X def) {
		T rt = this.getProperty(key, false);
		if (rt == null) {
			return def;
		}
		return (X) rt;
	}

	@Override
	public T getProperty(String key) {
		return this.getProperty(key, false);
	}

	/* */
	@Override
	public T getProperty(String key, boolean force) {

		T rt = this.doGetProperty(key);
		if (rt == null && force) {
			throw new GsException("force,no value for key:" + key
					+ ",all keys:" + this.keyList());
		}

		return rt;

	}

	protected abstract T doGetProperty(String key);

	@Override
	public boolean getPropertyAsBoolean(String key, boolean def) {
		T rt = this.getProperty(key);
		if (rt == null) {
			return def;
		}
		return Boolean.valueOf("" + rt);
	}

	/* */
	@Override
	public <X extends T> void setProperties(PropertiesI<X> pts) {
		this.setProperties(pts.getAsMap());
	}

	/* */
	@Override
	public <X extends T> void setProperties(Map<String, X> map) {
		for (Map.Entry<String, X> e : map.entrySet()) {
			this.setProperty(e);//
		}
	}

	@Override
	public <X extends T> void setProperty(Map.Entry<String, X> entry) {
		this.setProperty(entry.getKey(), entry.getValue());
	}

	/* */
	@Override
	public Map<String, T> getAsMap() {

		Map<String, T> rt = new HashMap<String, T>();
		List<String> kL = this.keyList();
		if(kL == null){
			throw new GsException();
		}
		for (String key : kL) {
			rt.put(key, this.getProperty(key));

		}
		return rt;

	}

	@Override
	public boolean equals(Object kvs) {
		if (kvs == null || !(kvs instanceof PropertiesI)) {
			return false;
		}
		PropertiesI pts = (PropertiesI) kvs;
		List<String> kL = pts.keyList();
		List<String> kL2 = this.keyList();
		if (kL.size() != kL2.size()) {
			return false;
		}
		for (String k : kL) {
			Object o1 = this.getProperty(k);
			Object o2 = pts.getProperty(k);
			if (!ObjectUtil.nullSafeEquals(o1, o2)) {
				return false;
			}
		}
		return true;

	}

	@Override
	public int getPropertyAsInt(String key, int def) {
		Object v = this.getProperty(key);
		if (v == null) {
			return def;
		}
		return Integer.parseInt("" + v);

	}

	@Override
	public PropertiesI<T> mergeFrom(PropertiesI<T> pts) {
		PropertiesI<T> rt = this.copy();
		rt.setProperties(pts);
		return rt;
	}

	@Override
	public PropertiesI<T> copy() {
		PropertiesI<T> rt = DefaultProperties.valueOf(this);
		rt.setProperties(this);
		return rt;
	}
}
