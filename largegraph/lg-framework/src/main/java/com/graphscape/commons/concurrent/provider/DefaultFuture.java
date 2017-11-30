/**
 * Dec 22, 2013
 */
package com.graphscape.commons.concurrent.provider;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

import com.graphscape.commons.concurrent.FutureI;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.lang.Wrapper;
import com.graphscape.commons.util.TimeAndUnit;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class DefaultFuture<T> extends Wrapper<Future<T>> implements FutureI<T> {

	/**
	 * @param t
	 */
	public DefaultFuture(Future<T> t) {
		super(t);
	}

	@Override
	public T get(TimeAndUnit tu) {
		try {
			return this.target.get(tu.getValue(), tu.getUnit());
		} catch (InterruptedException e) {
			throw GsException.toRuntimeException(e);
		} catch (ExecutionException e) {
			throw GsException.toRuntimeException(e);
		} catch (TimeoutException e) {
			throw GsException.toRuntimeException(e);
		}

	}

	@Override
	public T get() {
		try {
			return this.target.get();
		} catch (InterruptedException e) {
			throw GsException.toRuntimeException(e);
		} catch (ExecutionException e) {
			throw GsException.toRuntimeException(e);
		}

	}

	/**
	 * @param rt
	 * @return
	 */
	public static <T> FutureI<T> valueOf(Future<T> rt) {
		return new DefaultFuture<T>(rt);

	}

}
