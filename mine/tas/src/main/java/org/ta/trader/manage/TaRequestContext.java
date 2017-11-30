package org.ta.trader.manage;

import org.ta.common.data.TaObjectData;

public class TaRequestContext {

	private TaObjectData request;

	private TaObjectData response;
	private TaUserSession session;

	public TaRequestContext(TaObjectData request, TaObjectData response) {
		this.request = request;
		this.response = response;
	}

	public void setSession(TaUserSession session) {
	}

	public TaObjectData getRequest() {
		return request;
	}

	public TaObjectData getResponse() {
		return response;
	}

	public TaUserSession getSession() {
		return session;
	}
}
