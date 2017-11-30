package org.ta.client;

public interface TaRequester {

	public <T extends TaRequester> T execute();
	
}
