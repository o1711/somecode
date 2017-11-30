/**
 * Dec 29, 2013
 */
package com.graphscape.largegraph.client.provider.support;

import com.graphscape.commons.concurrent.FutureI;
import com.graphscape.commons.lang.CallbackI;
import com.graphscape.commons.lang.ErrorInfos;
import com.graphscape.commons.lang.WorkerI;
import com.graphscape.commons.message.MessageI;
import com.graphscape.commons.util.ProxyFuture4;
import com.graphscape.largegraph.client.ClientI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public abstract class RequesterSupport<T> implements WorkerI<T> {
	protected ClientI client;

	protected boolean force;

	public RequesterSupport(ClientI client) {
		this.client = client;
	}

	@Override
	public FutureI<T> submit() {
		MessageI msg = this.buildRequest();
		FutureI<MessageI> fM = this.client.putMessage(msg);

		CallbackI<MessageI, T> callback = new CallbackI<MessageI, T>() {

			@Override
			public T execute(MessageI t) {
				return convert(t);
			}
		};

		ProxyFuture4<MessageI, T> rtF = new ProxyFuture4<MessageI, T>(fM, callback);
		return rtF;
	}

	public abstract MessageI buildRequest();

	public T convert(MessageI t) {
		ErrorInfos eis = t.getErrorInfos();
		eis.assertNoError();//
		boolean isVoid = (Boolean) t.getPayload("isVoid", Boolean.FALSE);

		if (isVoid) {
			return null;
		}

		boolean isNull = (Boolean) t.getPayload("isNull", true);
		if (isNull) {
			return null;
		}
		return this.doConvert(t);
	}

	public abstract T doConvert(MessageI response);

}
