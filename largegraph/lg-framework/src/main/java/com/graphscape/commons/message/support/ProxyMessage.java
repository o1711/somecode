package com.graphscape.commons.message.support;

import java.util.Map;

import com.graphscape.commons.lang.ErrorInfos;
import com.graphscape.commons.lang.PropertiesI;
import com.graphscape.commons.lang.support.ProxyHasAttribute;
import com.graphscape.commons.message.MessageI;
import com.graphscape.commons.util.ObjectUtil;
import com.graphscape.commons.util.Path;

/**
 * @author wuzhen
 * 
 */
public class ProxyMessage extends ProxyHasAttribute implements MessageI {

	protected MessageI target;

	public ProxyMessage(MessageI target) {
		super(target);
		this.target = target;
	}

	@Override
	public PropertiesI<String> getHeaders() {
		//
		return this.target.getHeaders();
		//
	}

	@Override
	public PropertiesI<Object> getPayloads() {
		//
		return this.target.getPayloads();
		//
	}

	@Override
	public String getHeader(String key) {
		//
		return this.target.getHeader(key);
		//
	}

	@Override
	public void setHeader(String key, String value) {
		//
		this.target.setHeader(key, value);
		//
	}

	@Override
	public Object getPayload(String key) {
		//
		return this.target.getPayload(key);
		//
	}

	@Override
	public void setPayload(String key, Object value) {
		//
		this.target.setPayload(key, value);
		//
	}

	@Override
	public String toString() {
		return this.target.toString();
	}

	/*
	 * 
	 */
	@Override
	public String getHeader(String key, boolean force) {
		return this.target.getHeader(key, force);
	}

	/*
	 * 
	 */
	@Override
	public String getHeader(String key, String def) {
		//
		return this.target.getHeader(key, def);

	}

	/*
	 * 
	 */
	@Override
	public Object getPayload(String key, boolean force) {
		return this.target.getPayload(key, force);
	}

	/*
	 * 
	 */
	@Override
	public <T> T getPayload(Class<T> cls, String key, T def) {
		return this.target.getPayload(cls, key, def);
	}

	/*
	 * 
	 */
	@Override
	public void setPayloads(PropertiesI<Object> pls) {
		this.target.setPayloads(pls);
	}

	/*
	 * 
	 */
	@Override
	public void setMessage(MessageI msg) {
		this.target.setMessage(msg);
	}

	/*
	 * 
	 */
	@Override
	public void setHeaders(PropertiesI<String> hds) {
		this.target.setHeaders(hds);
	}

	/*
	 * 
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof ProxyMessage)) {
			return false;
		}

		return ObjectUtil.nullSafeEquals(this.target, ((ProxyMessage) obj).target);

	}

	/*
	 * 
	 */
	@Override
	public Object getPayload(String key, Object def) {
		//
		return this.target.getPayload(key, def);
	}

	/*
	 * 
	 */
	@Override
	public String getString(String key) {
		//
		return this.target.getString(key);

	}

	/*
	 * 
	 */
	@Override
	public String getString(String key, boolean force) {
		//
		return this.target.getString(key, force);

	}

	/*
	 */
	@Override
	public String getString(String key, String def) {
		return this.target.getString(key, def);
	}

	@Override
	public Path getPath() {
		return this.target.getPath();
	}

	@Override
	public String getId() {
		return this.target.getId();
	}

	@Override
	public boolean getBoolean(String key, boolean def) {
		return this.target.getBoolean(key, def);
	}

	public MessageI getTarget() {
		return this.target;
	}

	@Override
	public PropertiesI<Object> getAsFlatProperties() {
		return this.target.getAsFlatProperties();
	}

	@Override
	public long getCreated() {
		return this.target.getCreated();
	}

	@Override
	public void setHeaders(Map<String, String> hds) {
		this.target.setHeaders(hds);
	}

	@Override
	public ErrorInfos getErrorInfos() {
		return this.target.getErrorInfos();
	}

	@Override
	public MessageI clone() {
		return this.target.clone();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hp.topology.commons.api.message.MessageI#assertNoError()
	 */
	@Override
	public MessageI assertNoError() {
		this.target.assertNoError();
		return this;
	}

	@Override
	public Path getParentIdPath() {
		return this.target.getParentIdPath();
	}

	@Override
	public Path getIdPath() {
		return this.target.getIdPath();
	}
}
