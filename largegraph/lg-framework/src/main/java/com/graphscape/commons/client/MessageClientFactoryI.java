/**
 *  
 */
package com.graphscape.commons.client;

import java.util.List;

import com.graphscape.commons.concurrent.FutureI;
import com.graphscape.commons.lang.LifeCycleI;

/**
 * @author wu TODO remove to test package
 */
public interface MessageClientFactoryI<T extends MessageClientI> extends LifeCycleI {

	public int size();

	public T getFirstClient();

	public T getLastClient();

	public T getRandomClient();

	public void removeRandomClient(boolean close);

	public T createClient();

	public FutureI<T> createAndConnectClient();

	public void removeClient(boolean close);

	public void removeClient(T client, boolean close);

	public int total();

	public List<T> getClientList();

}
