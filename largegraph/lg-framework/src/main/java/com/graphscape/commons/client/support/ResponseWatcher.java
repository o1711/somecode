package com.graphscape.commons.client.support;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.Semaphore;

import com.graphscape.commons.client.MessageClientI;
import com.graphscape.commons.concurrent.FutureI;
import com.graphscape.commons.concurrent.provider.DefaultFuture;
import com.graphscape.commons.lang.HandlerI;
import com.graphscape.commons.message.MessageI;
import com.graphscape.commons.util.Holder;
import com.graphscape.commons.util.Path;

public class ResponseWatcher {

	// TODO move to support class
	private Map<String, HandlerI<MessageI>> responseListenerMap = new HashMap<String, HandlerI<MessageI>>();

	private MessageClientI client;

	private ExecutorService executor;

	public ResponseWatcher(MessageClientI client) {
		this.client = client;
		this.executor = Executors.newSingleThreadExecutor();//
	}

	public FutureI<MessageI> putMessage(MessageI msg) {
		String id = msg.getId();
		final Semaphore sem = new Semaphore(0);
		final Holder<MessageI> msgH = new Holder<MessageI>();
		FutureTask<MessageI> rt = new FutureTask<MessageI>(new Callable<MessageI>() {

			@Override
			public MessageI call() throws Exception {
				sem.acquire();

				return msgH.getTarget();
			}

		});
		this.responseListenerMap.put(id, new HandlerI<MessageI>() {

			@Override
			public void handle(MessageI t) {
				msgH.setTarget(t);
				sem.release();
			}
		});
		this.executor.submit(rt);

		this.client.sendMessage(msg);
		return DefaultFuture.valueOf(rt);
	}

	public void onMessage(MessageI msg) {
		Path pPath = msg.getParentIdPath();
		String pId = pPath.getName();
		HandlerI<MessageI> hdl = this.responseListenerMap.get(pId);
		if (hdl == null) {
			return;
		}
		hdl.handle(msg);

	}

}
