/**
 * All right is from Author of the file,to be explained in comming days.
 * May 10, 2013
 */
package com.graphscape.commons.session;

import java.util.UUID;

import com.graphscape.commons.lang.support.HasAttributeSupport;
import com.graphscape.commons.util.Path;
import com.graphscape.commons.util.TimeAndUnit;

/**
 * @author wu
 * 
 */
public abstract class SessionSupport extends HasAttributeSupport implements
		SessionI {

	protected long created;

	protected long touched;

	protected long timeout;

	protected Path path;

	protected String id;

	public SessionSupport(Path group, TimeAndUnit timeout) {
		this(group, UUID.randomUUID().toString(), timeout);
	}

	public SessionSupport(Path group, String id, TimeAndUnit timeout) {
		this.path = group;
		this.timeout = timeout.toMillis();
		this.id = id == null ? UUID.randomUUID().toString() : id;
		this.created = System.currentTimeMillis();
		this.touch();

	}

	@Override
	public String getId() {
		return this.id;
	}

	/*
	 * May 10, 2013
	 */
	@Override
	public boolean isTimeout() {
		//
		long now = System.currentTimeMillis();
		return now > this.touched + this.timeout;
	}

	/*
	 * May 10, 2013
	 */
	@Override
	public long getIdleTimeoutMs() {
		//
		return this.timeout;
	}

	/*
	 * May 10, 2013
	 */
	@Override
	public Path getPath() {
		//
		return this.path;
	}

	/*
	 * May 10, 2013
	 */
	@Override
	public long getCreated() {
		//
		return this.created;
	}

	/*
	 * May 10, 2013
	 */
	@Override
	public long getTouched() {
		//
		return this.touched;
	}

	/*
	 * May 10, 2013
	 */
	@Override
	public void touch() {
		//
		this.touched = System.currentTimeMillis();
	}

}
