package com.graphscape.commons.lang.support;

import com.graphscape.commons.lang.HasAttributeI;
import com.graphscape.commons.lang.PropertiesI;
import com.graphscape.commons.lang.Wrapper;

public class ProxyHasAttribute extends Wrapper<HasAttributeI> implements HasAttributeI {

	public ProxyHasAttribute(HasAttributeI t) {
		super(t);
	}

	@Override
	public <T> T getAttribute(String key) {
		// TODO Auto-generated method stub
		return this.target.getAttribute(key);
	}

	@Override
	public <T> T getAttribute(String key, T def) {
		// TODO Auto-generated method stub
		return this.target.getAttribute(key, def);
	}

	@Override
	public <T> T getAttribute(String key, boolean force) {
		// TODO Auto-generated method stub
		return this.target.getAttribute(key, force);
	}

	@Override
	public <T> T setAttribute(String key, T t) {
		// TODO Auto-generated method stub
		return this.target.setAttribute(key, t);
	}

	@Override
	public void setAttributes(PropertiesI<Object> pts) {
		this.target.setAttributes(pts);
	}

}
