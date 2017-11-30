package org.ta.client;

import org.ta.common.data.TaArrayData;
import org.ta.common.data.TaObjectData;

public class TaTickRequester extends TaBaseRequester {

	public TaTickRequester(TaClient client) {
		super(client, "tick");

	}

	public TaTickRequester orderInfos(TaArrayData orderInfoArray){
		this.request.property("orderInfoArray", orderInfoArray);
		return this;
	}
	@Override
	public TaTickRequester accountInfo(TaObjectData accountInfo) {
		super.accountInfo(accountInfo);
		return this;
	}
	
	public TaTickRequester ask(double ask){
		this.request.property("ask", ask);
		return this;
	}
	
	public TaTickRequester bid(double bid){
		this.request.property("bid", bid);
		return this;
	}
	

	public TaTickRequester time(long time) {
		this.request.propertyTime("time", time);
		return this;
	}

	public TaTickRequester tohlcv(long time, double o, double h, double l, double c, double volume) {
		this.time(time);
		this.open(o);
		this.high(h);
		this.low(l);
		this.close(c);
		this.volume(volume);
		return this;
	}

	public TaTickRequester high(double high) {
		this.request.property("high", high);
		return this;
	}

	public TaTickRequester low(double low) {
		this.request.property("low", low);
		return this;
	}

	public TaTickRequester open(double open) {
		this.request.property("open", open);
		return this;
	}

	public TaTickRequester close(double close) {
		this.request.property("close", close);
		return this;
	}

	public TaTickRequester volume(double volume) {
		this.request.property("volume", volume);
		return this;
	}

	public String getSessionId() {
		//
		return response.getPropertyAsString("sessionId");

	}

}
