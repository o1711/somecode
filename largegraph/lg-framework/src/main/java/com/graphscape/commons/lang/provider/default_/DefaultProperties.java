/**
 * Jun 10, 
 */
package com.graphscape.commons.lang.provider.default_;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.lang.PropertiesI;
import com.graphscape.commons.lang.support.PropertiesSupport;
import com.graphscape.commons.util.ObjectUtil;

/**
 * @author wu
 * 
 */
public class DefaultProperties<T> extends PropertiesSupport<T> {

	protected Map<String, T> map;

	public DefaultProperties() {
		this(new HashMap<String, T>());
	}

	public DefaultProperties(PropertiesI<T> pts) {
		this(pts.getAsMap());
	}

	public DefaultProperties(Map<String, T> map) {
		this.map = new HashMap<String, T>();
		this.map.putAll(map);

	}

	public static <T> DefaultProperties<T> valueOf(Map<String, T> map) {
		return new DefaultProperties<T>(map);
	}

	public static <T> DefaultProperties<T> valueOf(Object... keyValues) {
		DefaultProperties<T> rt = new DefaultProperties<T>();
		rt.setPropertiesByArray(keyValues);
		return rt;
	}

	public static <T> DefaultProperties<T> valueOf(PropertiesI<T> pts) {

		return new DefaultProperties<T>(pts.getAsMap());

	}

	/* */
	@Override
	public void setProperty(String key, T value) {
		this.map.put(key, value);
	}

	/* */
	@Override
	public T doGetProperty(String key) {

		T rt = this.map.get(key);
		return rt;

	}

	protected Object getSource() {
		return null;
	}

	public <X> X getProperty(Class<X> cls, String key) {
		return (X) this.getProperty(key);
	}

	/* */
	@Override
	public List<String> keyList() {
		List<String> rt = new ArrayList<String>();
		rt.addAll(this.map.keySet());
		return rt;

	}

	public <X> List<X> getPropertyList(Class<X> cls) {
		List<X> rt = new ArrayList<X>();
		for (Map.Entry<String, T> e : this.map.entrySet()) {
			Object o = e.getValue();
			if (cls.isInstance(o)) {
				rt.add(cls.cast(o));
			}
		}
		return rt;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof PropertiesI)) {
			return false;
		}
		PropertiesI pts = (PropertiesI) obj;
		List<String> kl1 = this.keyList();
		List<String> kl2 = pts.keyList();
		if (kl1.size() != kl2.size()) {
			return false;

		}
		for (String key : kl1) {
			Object v1 = this.getProperty(key);
			Object v2 = pts.getProperty(key);
			if (!ObjectUtil.nullSafeEquals(v1, v2)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		return this.map.toString();
	}

	/**
	 * @return
	 */
	public static <T> DefaultProperties<T> empty(Class<T> cls) {
		// TODO Auto-generated method stub
		return new DefaultProperties<T>();
	}

}
