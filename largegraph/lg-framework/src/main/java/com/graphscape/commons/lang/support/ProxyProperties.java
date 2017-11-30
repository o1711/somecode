/**
 * Dec 22, 2013
 */
package com.graphscape.commons.lang.support;

import java.util.List;

import com.graphscape.commons.lang.PropertiesI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class ProxyProperties<T> extends PropertiesSupport<T> {
	protected PropertiesI<T> target;

	public ProxyProperties(PropertiesI<T> t) {
		this.target = t;
	}

	@Override
	public void setProperty(String key, T value) {
		this.target.setProperty(key, value);
	}

	@Override
	public List<String> keyList() {
		// TODO Auto-generated method stub
		return this.target.keyList();
	}

	@Override
	protected T doGetProperty(String key) {
		return this.target.getProperty(key);

	}

}
