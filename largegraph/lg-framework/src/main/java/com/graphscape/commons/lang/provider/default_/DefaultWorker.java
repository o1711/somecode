/**
 * Dec 21, 2013
 */
package com.graphscape.commons.lang.provider.default_;

import java.util.concurrent.Callable;

import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.lang.support.ThreadWorkerSupport;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class DefaultWorker<T> extends ThreadWorkerSupport<T> {
	private Callable<T> call;

	public DefaultWorker(Callable<T> call) {
		this.call = call;
	}

	@Override
	protected T call() {
		try {
			return this.call.call();
		} catch (Exception e) {
			throw GsException.toRuntimeException(e);
		}
	}

}
