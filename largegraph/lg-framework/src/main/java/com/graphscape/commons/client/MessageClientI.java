/**
 *  
 */
package com.graphscape.commons.client;

import com.graphscape.commons.concurrent.FutureI;
import com.graphscape.commons.message.MessageI;

/**
 * @author wu
 * 
 */
public interface MessageClientI {

	public String getName();

	public FutureI<MessageClientI> connect(String credentical);

	public FutureI<MessageI> putMessage(MessageI msg);

	public void sendMessage(MessageI msg);

	public boolean isConnected();
	
	public FutureI<MessageClientI> disconnect();
	
	public void close();

	public int getIdleTime();

}
