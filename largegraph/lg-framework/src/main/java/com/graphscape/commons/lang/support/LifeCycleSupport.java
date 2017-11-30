package com.graphscape.commons.lang.support;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.Semaphore;

import com.graphscape.commons.lang.Enumeration;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.lang.HandlerI;
import com.graphscape.commons.lang.LifeCycleI;
import com.graphscape.commons.util.ObjectUtil;

public abstract class LifeCycleSupport extends HasAttributeSupport implements LifeCycleI {

	public static class Status extends Enumeration {

		public Status(String name) {
			super(name);
		}

		public static Status valueOf(String name) {
			return new Status(name);
		}

	}

	public static class StatusChange {

		private Status oldStatus;

		private Status newStatus;

		/**
		 * @param old
		 * @param st
		 */
		public StatusChange(Status old, Status st) {
			this.oldStatus = old;
			this.newStatus = st;
		}

		@Override
		public int hashCode() {
			return this.oldStatus.hashCode() + this.newStatus.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof StatusChange)) {
				return false;
			}
			StatusChange sc = (StatusChange) obj;

			return ObjectUtil.nullSafeEquals(this.oldStatus, sc.oldStatus)
					&& ObjectUtil.nullSafeEquals(this.newStatus, sc.newStatus);

		}

		/**
		 * @param old
		 * @param st
		 * @return
		 */
		public static StatusChange valueOf(Status old, Status st) {
			StatusChange rt = new StatusChange(old, st);
			return rt;
		}

	}

	public static final Status INITIAL = Status.valueOf("initial");

	public static final Status STOPED = Status.valueOf("stoped");

	public static final Status RUNNING = Status.valueOf("running");

	public static final Status STARTING = Status.valueOf("starting");

	protected Status status = STOPED;

	protected Map<Status, Semaphore> statusWatcher = new HashMap<Status, Semaphore>();

	public Map<StatusChange, HandlerI<StatusChange>> statusHandler = new HashMap<StatusChange, HandlerI<StatusChange>>();

	public boolean isRunning() {
		return this.isStatus(RUNNING);
	}

	public boolean isStarting() {
		return this.isStatus(STARTING);
	}

	public boolean isStatus(Status st) {
		return st.equals(this.status);
	}

	public void assertIsRunning() {
		if (this.isRunning()) {
			return;
		}
		throw new GsException("illegal status:" + this.status);
	}

	public void addStateChangeHandler(StatusChange sc, HandlerI<StatusChange> hdl) {
		this.statusHandler.put(sc, hdl);
	}

	@Override
	public void start() {
		if (this.isRunning() || this.isStarting()) {
			throw new GsException("cannot perform the operation on life cycle:" + this + ",status conflict:"
					+ this.status);
		}
		this.setStatus(STARTING);
		try {
			this.doStart();
			this.setStatus(RUNNING);
		} finally {

		}
	}

	@Override
	public Future<LifeCycleI> join() {

		FutureTask<LifeCycleI> rt = new FutureTask<LifeCycleI>(new Callable<LifeCycleI>() {

			@Override
			public LifeCycleI call() throws Exception {

				return null;
			}

		});

		return rt;
	}

	public void setStatus(Status st) {
		Status old = this.status;
		this.status = st;
		this.stateChanged(old, this.status);
	}

	public void stateChanged(Status oldS, Status newS) {
		StatusChange sc = StatusChange.valueOf(oldS, newS);
		HandlerI<StatusChange> hdl = this.statusHandler.get(sc);
		if (hdl != null) {
			hdl.handle(sc);
		}
	}

	protected abstract void doStart();

	protected abstract void doShutdown();

	@Override
	public void shutdown() {
		if (!this.isRunning()) {

		}
		try {
			this.doShutdown();
		} finally {

		}
	}

}
