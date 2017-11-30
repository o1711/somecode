/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 11, 2012
 */
package com.graphscape.commons.comet;

import java.io.Reader;


/**
 * @author wu
 */
public interface CometListenerI {


	public void onConnect(CometI ws);

	public void onMessage(CometI ws, Reader reader);
	
	public void onMessage(CometI ws, Object msg);
	
	public void onException(CometI ws, Throwable t);

	public void onClose(CometI ws, int statusCode, String reason);

}
