/**
 * Dec 21, 2013
 */
package com.graphscape.commons.lang.support;

import com.graphscape.commons.lang.EnvironmentI;
import com.graphscape.commons.lang.PropertiesI;
import com.graphscape.commons.lang.Wrapper;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class ProxyEnvironment extends Wrapper<EnvironmentI> implements EnvironmentI {

	public ProxyEnvironment(EnvironmentI t) {
		super(t);
	}

	@Override
	public <T> T getAttribute(String key) {
		return this.target.getAttribute(key);
	}

	@Override
	public <T> T getAttribute(String key, T def) {
		// TODO Auto-generated method stub
		return this.target.getAttribute(key, def);
	}

	@Override
	public <T> T getAttribute(String key, boolean force) {
		return this.target.getAttribute(key, force);
	}

	@Override
	public <T> T setAttribute(String key, T t) {
		// TODO Auto-generated method stub
		return this.target.setAttribute(key, t);
	}

	@Override
	public <T> T getService(Class<T> cls, boolean force) {
		// TODO Auto-generated method stub
		return this.target.getService(cls, force);
	}

	@Override
	public <T> T getService(Class<T> cls) {
		// TODO Auto-generated method stub
		return this.target.getService(cls);
	}

	@Override
	public String getVariable(String key, boolean force) {
		// TODO Auto-generated method stub
		return this.target.getVariable(key, force);
	}

	@Override
	public String getVariable(String key) {
		// TODO Auto-generated method stub
		return this.target.getVariable(key);
	}

	@Override
	public void setAttributes(PropertiesI<Object> pts) {
		this.target.setAttributes(pts);
	}

}
