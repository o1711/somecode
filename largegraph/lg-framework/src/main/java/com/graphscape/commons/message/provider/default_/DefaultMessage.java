/**

 */
package com.graphscape.commons.message.provider.default_;

import java.util.Map;
import java.util.UUID;

import com.graphscape.commons.lang.ErrorInfos;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.lang.PropertiesI;
import com.graphscape.commons.lang.provider.default_.DefaultProperties;
import com.graphscape.commons.lang.support.HasAttributeSupport;
import com.graphscape.commons.message.MessageI;
import com.graphscape.commons.util.Path;

/**
 * @author wuzhen
 * 
 */
public class DefaultMessage extends HasAttributeSupport implements MessageI {

	private PropertiesI<String> headers;

	private PropertiesI<Object> payloads;

	private ErrorInfos errorInfos;

	protected Path cache_Path;

	protected Long cache_Created;

	private DefaultMessage() {
		this(Path.ROOT);
	}

	private DefaultMessage(Path path) {
		this(path, null);
	}

	private DefaultMessage(Path path, String id) {
		this(null, path, id);
	}

	public DefaultMessage(MessageI parent) {
		this(parent, Path.ROOT);
	}

	public DefaultMessage(MessageI parent, Path path) {
		this(parent, path, null);
	}

	public DefaultMessage(MessageI parent, Path path, String id) {
		this.headers = new DefaultProperties<String>();
		this.payloads = new DefaultProperties<Object>();

		this.headers.setProperty(HK_PATH, path.toString());
		id = id == null ? UUID.randomUUID().toString() : id;
		this.headers.setProperty(HK_ID, id);
		this.headers.setProperty(HK_CREATED, System.currentTimeMillis() + "");

		Path idP = parent == null ? Path.ROOT : parent.getIdPath();
		this.headers.setProperty(HK_PIDP, idP.toString());
		this.errorInfos = new ErrorInfos();
	}

	protected DefaultMessage(PropertiesI<String> headers, PropertiesI<Object> pls) {
		this(headers, pls, new ErrorInfos());
	}

	protected DefaultMessage(PropertiesI<String> headers, PropertiesI<Object> pls, ErrorInfos eis) {
		this.headers = headers;
		this.payloads = pls;
		this.errorInfos = eis;
	}

	/* */
	@Override
	public PropertiesI<String> getHeaders() {

		return this.headers;

	}

	/* */
	@Override
	public PropertiesI<Object> getPayloads() {

		return this.payloads;

	}

	@Override
	public String getHeader(String key) {
		//
		return this.headers.getProperty(key);
		//
	}

	@Override
	public void setHeader(String key, String value) {
		//
		this.headers.setProperty(key, value);//
	}

	@Override
	public Object getPayload(String key) {
		//
		return this.payloads.getProperty(key);
		//
	}

	@Override
	public void setPayload(String key, Object value) {
		//
		this.payloads.setProperty(key, value);
		//
	}

	/*
	
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof MessageI)) {
			return false;
		}
		MessageI m = (MessageI) obj;
		if (!m.getHeaders().equals(this.headers)) {
			return false;
		}
		if (!this.payloads.equals(m.getPayloads())) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("path:");
		sb.append(this.getPath());

		sb.append(",id:");
		sb.append(this.getId());

		sb.append(",headers:");
		sb.append(this.headers.toString());
		sb.append(",payloads:");
		sb.append(this.payloads.toString());
		return sb.toString();
	}

	/*
	 */
	@Override
	public String getHeader(String key, boolean force) {
		//
		String rt = this.getHeader(key);
		if (force && rt == null) {
			throw new GsException("no header found:" + key + " ,headers:" + this.getHeaders());
		}
		return rt;
	}

	/*
	 */
	@Override
	public String getHeader(String key, String def) {

		String rt = this.getHeaders().getProperty(key);
		if (rt == null) {
			return def;
		}
		return rt;
	}

	/*
	 */
	@Override
	public Object getPayload(String key, boolean force) {
		//
		Object rt = this.getPayloads().getProperty(key);
		if (force && rt == null) {
			throw new GsException("force key:" + key);
		}
		return rt;
	}

	/*
	 */
	@Override
	public <T> T getPayload(Class<T> cls, String key, T def) {
		//
		Object rt = this.getPayload(key);
		if (rt == null) {
			return def;
		}
		return (T) rt;
	}

	/*
	 */
	@Override
	public void setPayloads(PropertiesI<Object> pls) {
		this.payloads.setProperties(pls);
	}

	/*
	 */
	@Override
	public void setHeaders(PropertiesI<String> hds) {
		this.headers.setProperties(hds);
	}

	/*
	 */
	@Override
	public void setMessage(MessageI msg) {
		this.setHeaders(msg.getHeaders());
		this.setPayloads(msg.getPayloads());
	}

	public static MessageI newMessage(PropertiesI<String> hds, PropertiesI<Object> pls) {
		return new DefaultMessage(hds, pls);
	}

	/**
	 */
	public static MessageI newMessage(PropertiesI<String> hds, PropertiesI<Object> pls, ErrorInfos eis) {
		MessageI rt = new DefaultMessage(hds, pls, eis);

		return rt;
	}

	/*
	 */
	@Override
	public String getString(String key) {
		//
		return (String) this.getPayload(key);

	}

	/*
	 */
	@Override
	public String getString(String key, boolean force) {
		//
		return (String) this.getPayload(key, force);
	}

	@Override
	public Object getPayload(String key, Object def) {
		Object rt = this.getPayload(key);
		return rt == null ? def : rt;

	}

	/*
	 */
	@Override
	public String getString(String key, String def) {
		//
		return (String) this.getPayload(key, def);
	}

	@Override
	public String getId() {
		return this.getHeader(HK_ID);
	}

	@Override
	public Path getPath() {
		if (this.cache_Path != null) {
			return this.cache_Path;
		}
		String ps = this.getHeader(HK_PATH);
		this.cache_Path = Path.valueOf(ps);
		return this.cache_Path;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.commons.api.message.MessageI#getBoolean(java.lang.String,
	 * boolean)
	 */
	@Override
	public boolean getBoolean(String key, boolean def) {
		Object obj = this.getPayload(key);
		if (obj == null) {
			return def;
		}
		return (Boolean) obj;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hp.topology.commons.api.message.MessageI#getAsProperties()
	 */
	@Override
	public PropertiesI<Object> getAsFlatProperties() {
		PropertiesI<Object> rt = new DefaultProperties<Object>();
		// TODO
		return rt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hp.topology.commons.api.message.MessageI#getCreated()
	 */
	@Override
	public long getCreated() {
		if (this.cache_Created != null) {
			return this.cache_Created;
		}
		String rtS = this.getHeader(HK_CREATED);
		this.cache_Created = Long.parseLong(rtS);
		return this.cache_Created;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hp.topology.commons.api.message.MessageI#setHeaders(java.
	 * util.Map)
	 */
	@Override
	public void setHeaders(Map<String, String> hds) {
		this.headers.setProperties(hds);
	}

	@Override
	public ErrorInfos getErrorInfos() {
		return this.errorInfos;
	}

	@Override
	public MessageI clone() {
		return new DefaultMessage(this.getHeaders(), this.getPayloads());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hp.topology.commons.api.message.MessageI#assertNoError()
	 */
	@Override
	public MessageI assertNoError() {
		this.getErrorInfos().assertNoError();

		return this;

	}

	@Override
	public Path getParentIdPath() {
		String pidsS = this.getHeader(MessageI.HK_PIDP);
		if (pidsS == null) {
			return Path.ROOT;
		}
		return Path.valueOf(pidsS);
	}

	@Override
	public Path getIdPath() {
		return this.getParentIdPath().getSubPath(this.getId());
	}

}
