/**
 * 
 */
package com.graphscape.commons.client.support;

import com.graphscape.commons.client.MessageClientI;
import com.graphscape.commons.concurrent.FutureI;
import com.graphscape.commons.lang.Wrapper;
import com.graphscape.commons.message.MessageI;

/**
 * @author wuzhen
 * 
 */
public class ProxyMessageClient extends Wrapper<MessageClientI> implements MessageClientI {

	public ProxyMessageClient(MessageClientI t) {
		super(t);
	}

	@Override
	public String getName() {
		return this.getTarget().getName();
	}

	@Override
	public FutureI<MessageClientI> connect(String credentical) {
		return this.target.connect(credentical);
	}

	@Override
	public FutureI<MessageI> putMessage(MessageI msg) {
		return this.target.putMessage(msg);
	}

	@Override
	public void sendMessage(MessageI msg) {
		this.target.sendMessage(msg);
	}

	@Override
	public boolean isConnected() {
		return this.target.isConnected();
	}

	@Override
	public void close() {
		this.target.close();
	}

	@Override
	public int getIdleTime() {
		return this.target.getIdleTime();
	}

	/* (non-Javadoc)
	 * @see com.graphscape.commons.client.MessageClientI#disconnect()
	 */
	@Override
	public FutureI<MessageClientI> disconnect() {
		return this.target.disconnect();
	}

}
