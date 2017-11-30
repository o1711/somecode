/**
 * All right is from Author of the file,to be explained in comming days.
 * May 10, 2013
 */
package com.graphscape.commons.session.provider.default_;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.graphscape.commons.handling.support.CollectionListener;
import com.graphscape.commons.lang.HandlerI;
import com.graphscape.commons.session.SessionI;
import com.graphscape.commons.session.SessionManagerI;
import com.graphscape.commons.util.Path;
import com.graphscape.commons.util.TimeAndUnit;

/**
 * @author wu
 * 
 */
public class DefaultSessionManager implements SessionManagerI {

	private Path path;

	private Map<String, SessionI> sessions = Collections
			.synchronizedMap(new HashMap<String, SessionI>());

	private CollectionListener<SessionI> timeoutHandlers = new CollectionListener<SessionI>();

	private ExecutorService executor;

	private Map<String, SessionManagerI> sessionManagerMap = new HashMap<String, SessionManagerI>();

	public DefaultSessionManager() {
		this(Path.ROOT);
	}

	public DefaultSessionManager(Path name) {
		this.path = name;
	}

	/*
	 * May 10, 2013
	 */
	@Override
	public Path getPath() {
		//
		return path;
	}

	/*
	 * May 10, 2013
	 */
	@Override
	public SessionI getSession(String id) {
		//
		SessionI s = this.sessions.get(id);
		if (s != null && s.isTimeout()) {
			this.doTimeout(s);
			s = null;
		}
		return s;

	}

	/*
	 * May 10, 2013
	 */
	@Override
	public SessionI touchSession(String id) {
		SessionI rt = this.getSession(id);
		if (rt == null) {
			return null;
		}
		rt.touch();
		return rt;
	}

	/*
	 * May 10, 2013
	 */
	@Override
	public SessionI createSession(TimeAndUnit timeout) {
		//
		return this.createSession(UUID.randomUUID().toString(), timeout);
	}

	/*
	 * May 10, 2013
	 */
	@Override
	public SessionI createSession(String id, TimeAndUnit timeout) {
		SessionI rt = new DefaultSession(this.path, id, timeout);
		this.sessions.put(rt.getId(), rt);
		return rt;
	}

	public void addTimeoutHandler(HandlerI<SessionI> hdl) {
		//
		this.timeoutHandlers.addHandler(hdl);
	}

	public void checkTimeout() {
		List<SessionI> sL = new ArrayList<SessionI>();// copy

		for (SessionI s : this.sessions.values()) {
			if (s.isTimeout()) {
				sL.add(s);
			}
		}
		//
		for (SessionI s : sL) {
			this.doTimeout(s);
		}

	}

	private void doTimeout(SessionI s) {
		this.sessions.remove(s.getId());
		this.timeoutHandlers.handle(s);
	}

	@Override
	public SessionManagerI createManager(String name) {
		SessionManagerI rt = new DefaultSessionManager(
				this.path.getSubPath(name));
		this.sessionManagerMap.put(name, rt);
		return rt;
	}

	@Override
	public SessionManagerI getManager(String name) {
		return this.sessionManagerMap.get(name);

	}

	protected void doStart() {
		//
		this.executor = Executors.newSingleThreadExecutor();

		this.executor.submit(new Callable() {

			@Override
			public Object call() throws Exception {
				//
				// SessionServerImpl.this.run();
				return null;
			}
		});

	}

	/**
	 * May 10, 2013
	 */
	protected void run() {
	}
}
