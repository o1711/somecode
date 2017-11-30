/**
 * Jun 13, 2012
 */
package com.graphscape.gwt.core;

import com.graphscape.gwt.core.UiException;
import com.graphscape.gwt.core.commons.Path;
import com.graphscape.gwt.core.data.PropertiesData;
import com.graphscape.gwt.core.data.message.MessageData;
import com.graphscape.gwt.core.data.property.ObjectPropertiesData;

/**
 * @author wuzhen
 * 
 */
public class MsgWrapper extends PropertiesData<Object> {

	public static final String HK_REQUEST_ID = "_requestId";
	
	protected MessageData target;

	public MsgWrapper(String path) {
		this(Path.valueOf(path));
	}

	public MsgWrapper(Path path) {
		this(new MessageData(path));
	}

	public MsgWrapper(MessageData md) {
		this.target = md;
	}

	public String getHeader(String key, boolean force) {
		return this.target.getHeader(key, force);
	}

	public String getHeader(String key) {
		return this.target.getHeader(key);
	}

	/**
	 * @param headers
	 *            the header to set
	 */
	public void setHeader(String key, String value) {
		this.target.setHeader(key, value);
	}

	/**
	 * @return the payload
	 */
	public ObjectPropertiesData getPayloads() {
		return this.target.getPayloads();
	}

	/**
	 * @param payload
	 *            the payload to set
	 */
	public void setPayloads(ObjectPropertiesData pts) {
		this.target.setPayloads(pts);
	}

	public <T> T getPayload(String key, boolean force) {
		Object rt = this.getPayloads().getProperty(key, force);

		return (T) rt;
	}

	public void setPayload(String key, boolean value) {
		this.target.setPayload(key, value);
	}

	public void setPayload(String key, String value) {

		this.target.setPayload(key, (value));

	}

	public void setPayload(String key, Object value) {
		this.target.setPayload(key, value);
	}

	public String getPayLoadAsString(String key, boolean force) {
		String sd = this.getPayload(key, force);
		String rt = sd == null ? null : sd;
		if (rt == null && force) {
			throw new UiException("no payload:" + key);
		}
		return rt;

	}

	public Boolean getPayLoadAsBoolean(String key, Boolean def) {
		Boolean rt = this.getPayLoadAsBoolean(key, false);
		return rt == null ? def : rt;
	}

	public Boolean getPayLoadAsBoolean(String key, boolean force) {
		Boolean sd = this.getPayload(key, false);
		Boolean rt = sd == null ? null : sd;

		if (rt == null && force) {
			throw new UiException("no payload:" + key);
		}

		return rt;

	}

	public Path getPath() {
		return this.target.getPath();
	}

	/**
	 * @return the target
	 */
	public MessageData getTarget() {
		return target;
	}

	/**
	 * @return
	 */
	public MessageData getMessage() {
		return this.getTarget();
	}

	@Override
	public String toString() {
		return "MsgWarpper,target:" + this.target.toString();
	}
}
