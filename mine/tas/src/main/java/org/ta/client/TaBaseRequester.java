package org.ta.client;

import org.ta.common.config.TaException;
import org.ta.common.data.TaObjectData;
import org.ta.common.handler.TaHandler;

public abstract class TaBaseRequester implements TaRequester, TaHandler<TaObjectData> {

	protected TaClient client;

	protected TaObjectData request;

	protected TaObjectData response;

	public TaBaseRequester(TaClient client, String handler) {
		this.client = client;
		this.request = new TaObjectData();
		this.request.set("handler", handler);
	}

	@Override
	public <T extends TaRequester> T execute() {
		this.client.doRequest(request, this);//
		String erroCode = this.response.getPropertyAsString("errorCode");

		if (erroCode != null) {
			throw new TaException("errorCode:" + erroCode);
		}
		return (T) this;
	}

	@Override
	public void handle(TaObjectData t) {
		this.response = t;
	}

	public TaBaseRequester accountInfo(TaObjectData accountInfo) {
		this.request.property("accountInfo", accountInfo);
		return this;
	}

	public TaObjectData getResponse() {
		return response;
	}

}
