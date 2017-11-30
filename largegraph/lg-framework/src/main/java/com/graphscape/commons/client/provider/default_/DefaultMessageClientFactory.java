/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 25, 2013
 */
package com.graphscape.commons.client.provider.default_;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.graphscape.commons.client.MessageClientFactoryI;
import com.graphscape.commons.client.MessageClientI;
import com.graphscape.commons.client.MessageClientServiceProviderI;
import com.graphscape.commons.client.provider.default_.ajax.AjaxMessageClient;
import com.graphscape.commons.concurrent.FutureI;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.lang.support.LifeCycleSupport;
import com.graphscape.commons.util.ProxyFuture3;

/**
 * @author wu
 * TODO move to test dir.
 */
public class DefaultMessageClientFactory<T extends MessageClientI> extends LifeCycleSupport implements MessageClientFactoryI<T> {

	private List<T> clientList;

	private int next;

	protected MessageClientServiceProviderI mcspi;

	public DefaultMessageClientFactory(MessageClientServiceProviderI mcspi) {
		this.mcspi = mcspi;
		this.clientList = new ArrayList<T>();
		this.clientList = Collections.synchronizedList(this.clientList);

	}

	public int size() {
		return this.clientList.size();
	}

	public synchronized T getFirstClient() {
		synchronized (this.clientList) {
			if (this.size() == 0) {
				return null;
			}
			return this.clientList.get(0);
		}
	}

	public synchronized T getLastClient() {
		synchronized (this.clientList) {
			if (this.size() == 0) {
				return null;
			}
			return this.clientList.get(this.clientList.size() - 1);
		}
	}

	public T getRandomClient() {
		synchronized (this.clientList) {

			if (this.size() == 0) {
				return null;
			}
			int idx = new Random().nextInt(this.size() - 1);
			return this.clientList.get(idx);
		}
	}

	public void removeRandomClient(boolean close) {
		T c = this.getRandomClient();
		this.removeClient(c, close);
	}

	@Override
	public T createClient() {

		final T client = this.newClient(this.next++);

		//
		this.clientList.add(client);
		return client;
	}

	protected T newClient(int idx) {
		String name = "client-" + idx;

		MessageClientI client = new AjaxMessageClient(this.mcspi);
		T rt = this.mcspi.newClientWrapper(client);

		return rt;
	}

	public void removeClient(boolean close) {
		T c = this.getFirstClient();
		this.removeClient(c, close);
	}

	public void removeClient(T client, boolean close) {
		boolean removed = this.clientList.remove(client);
		if (!removed) {
			throw new GsException("no this client:" + client);
		}
		if (close) {
			client.close();
		}
	}

	@Override
	public void doShutdown() {
		while (true) {
			T c = this.getFirstClient();
			if (c == null) {
				break;
			}
			if (c.isConnected()) {
				c.close();
			}
			this.removeClient(c, false);

		}
	}

	/**
	 * @return
	 */
	public int total() {
		return this.next;
	}

	/*
	 * Jan 26, 2013
	 */
	@Override
	public List<T> getClientList() {
		//
		return this.clientList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.lang.LifeCycleI#start()
	 */
	@Override
	public void doStart() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.commons.client.MessageClientFactoryI#createAndConnectClient
	 * ()
	 */
	@Override
	public FutureI<T> createAndConnectClient() {
		FutureI<MessageClientI> f = this.createClient().connect("TODO");

		return new ProxyFuture3<MessageClientI, T>(f);

	}

}
