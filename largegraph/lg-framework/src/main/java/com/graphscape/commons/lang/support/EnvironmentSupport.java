/**
 * Dec 21, 2013
 */
package com.graphscape.commons.lang.support;

import java.util.HashMap;
import java.util.Map;

import com.graphscape.commons.lang.EnvironmentI;
import com.graphscape.commons.lang.GsException;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class EnvironmentSupport extends HasAttributeSupport implements EnvironmentI {

	protected Map<Class, Object> serviceMap = new HashMap<Class, Object>();

	protected Map<String, String> varMap = new HashMap<String, String>();

	protected void addEnvironmentService(Class cls, Object obj) {
		this.serviceMap.put(cls, obj);
	}

	@Override
	public <T> T getService(Class<T> cls, boolean force) {
		Object rt = this.serviceMap.get(cls);
		if (rt == null && force) {
			throw new GsException("no service object for interface:" + cls);
		}
		return (T) rt;
	}

	@Override
	public <T> T getService(Class<T> cls) {
		return this.getService(cls, false);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.commons.lang.EnvironmentI#getVariable(java.lang.String,
	 * boolean)
	 */
	@Override
	public String getVariable(String key, boolean force) {
		String rt = this.varMap.get(key);
		if (rt == null && force) {
			throw new GsException("no var with key:" + key + ",in evironment:" + this);
		}
		return rt;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.commons.lang.EnvironmentI#getVariable(java.lang.String)
	 */
	@Override
	public String getVariable(String key) {
		// TODO Auto-generated method stub
		return this.getVariable(key, false);

	}

}
