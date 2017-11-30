/**
 * All right is from Author of the file,to be explained in comming days.
 * May 9, 2013
 */
package com.graphscape.gwt.core.provider.default_.comet;

import com.graphscape.gwt.core.HandlerI;
import com.graphscape.gwt.core.provider.default_.comet.GometI;

/**
 * @author wu
 * 
 */
public interface GometI {

	public String getProtocol();

	public void open(long timeoutMs);

	public void close();

	public void send(String jsS, HandlerI<String> onfailure);

	// regist listeners
	public void addOpenHandler(HandlerI<GometI> handler);

	public void addCloseHandler(HandlerI<String> handler);

	public void addErrorHandler(HandlerI<String> handler);

	public void addMessageHandler(HandlerI<String> handler);

	public boolean isOpen();

}
