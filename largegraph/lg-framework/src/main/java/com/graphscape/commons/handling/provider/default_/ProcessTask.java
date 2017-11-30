package com.graphscape.commons.handling.provider.default_;

import java.util.concurrent.Callable;

import com.graphscape.commons.handling.HandlingContextI;
import com.graphscape.commons.handling.spi.HandlingProviderI;
import com.graphscape.commons.lang.HandlerI;

public class ProcessTask<S, X, T extends HandlingContextI<S, X>> implements Callable<X> {

	private S req;

	private HandlingProviderI<S, X, T> mspi;

	ProcessTask(HandlingProviderI<S, X, T> mspi, S req) {
		this.req = req;
		this.mspi = mspi;
	}

	@Override
	public X call() throws Exception {

		T mc = this.mspi.getHandlingContextResolver().resolve(this.req);

		HandlerI<T> hdl = this.mspi.getHandlerResolver().resolve(mc);

		if (hdl == null) {
			mc.getErrorInfos().add("no handler found for request:" + this.req);
		} else {

			try {
				hdl.handle(mc);
			} catch (Exception e) {
				mc.getErrorInfos().add(e);
			}
		}

		X rt = mc.getOutputData();
		return rt;
	}

}
