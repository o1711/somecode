/**
 * Jun 13, 2012
 */
package org.cellang.clwt.core.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cellang.clwt.core.client.lang.HasProperties;
import org.cellang.clwt.core.client.util.ObjectUtil;

/**
 * @author wuzhen
 * 
 */
public class MapProperties<T> implements HasProperties<T> {

	private Map<String, T> map = new HashMap<String, T>();

	public static <T> MapProperties<T> valueOf(Object... obj) {
		MapProperties<T> rt = new MapProperties<T>();
		for (int i = 0; i < obj.length / 2; i++) {
			String key = (String) obj[i * 2];
			T value = (T) obj[i * 2 + 1];
			rt.setProperty(key, value);
		}
		return rt;
	}

	@Override
	public List<String> keyList() {
		List<String> rt = new ArrayList<String>();
		rt.addAll(map.keySet());
		return rt;
	}

	@Override
	public void setProperties(HasProperties<T> pts) {
		this.map.putAll(pts.getAsMap());//
	}

	@Override
	public T getProperty(String pname) {
		return this.getProperty(pname, false);
	}

	@Override
	public void setProperty(String key, T value) {
		this.map.put(key, value);
	}

	@Override
	public T getProperty(String key, boolean force) {
		T rt = this.getProperty(key, null);
		if (rt == null && force) {
			throw new UiException("force:" + key + ",keyList:" + this.keyList());
		}
		return rt;
	}

	@Override
	public T getProperty(String key, T def) {
		T rt = this.map.get(key);
		if (rt == null) {
			return def;
		}

		return rt;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof HasProperties)) {
			return false;
		}
		HasProperties pts = (HasProperties) obj;
		List<String> kl1 = this.keyList();
		List<String> kl2 = pts.keyList();
		if (kl1.size() != kl2.size()) {
			return false;
		}

		for (String key : this.keyList()) {
			Object d = this.getProperty(key);
			Object d2 = pts.getProperty(key);
			if (!ObjectUtil.nullSafeEquals(d, d2)) {
				return false;
			}
		}
		return true;

	}

	/* */
	@Override
	public Map<String, T> getAsMap() {
		return this.map;

	}

	/*
	 *Apr 9, 2013
	 */
	@Override
	public void setProperties(Map<String, T> map) {
		this.map.putAll(map);
	}

}
