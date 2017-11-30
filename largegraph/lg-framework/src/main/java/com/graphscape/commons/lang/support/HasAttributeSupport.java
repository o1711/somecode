/**
 *  
 */
package com.graphscape.commons.lang.support;

import java.util.HashMap;
import java.util.Map;

import com.graphscape.commons.lang.HasAttributeI;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.lang.PropertiesI;

/**
 * @author wu
 * 
 */
public class HasAttributeSupport extends AdaptableSupport implements HasAttributeI {

	protected Map<String, Object> attributes = new HashMap<String, Object>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hp.topology.commons.api.HasAttributeI#getAttribute(java.lang
	 * .String)
	 */
	@Override
	public <T> T getAttribute(String key) {
		return (T) this.attributes.get(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hp.topology.commons.api.HasAttributeI#getAttribute(java.lang
	 * .String, java.lang.Object)
	 */
	@Override
	public <T> T getAttribute(String key, T def) {
		T rt = this.getAttribute(key);

		return rt == null ? def : rt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hp.topology.commons.api.HasAttributeI#setAttribute(java.lang
	 * .Object)
	 */
	@Override
	public <T> T setAttribute(String key, T t) {

		return (T) this.attributes.put(key, t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hp.topology.commons.api.HasAttributeI#getAttribute(java.lang
	 * .String, boolean)
	 */
	@Override
	public <T> T getAttribute(String key, boolean force) {
		T rt = this.getAttribute(key);
		if (rt == null && force) {
			throw new GsException("no attribute:" + key);
		}
		return rt;
	}

	@Override
	public void setAttributes(PropertiesI<Object> pts) {
		this.attributes.putAll(pts.getAsMap());
	}

}
