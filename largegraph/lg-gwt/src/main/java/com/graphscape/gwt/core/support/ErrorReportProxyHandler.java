/**
 *  Jan 16, 2013
 */
package com.graphscape.gwt.core.support;

import com.graphscape.gwt.core.HandlerI;
import com.graphscape.gwt.core.UiException;
import com.graphscape.gwt.core.logger.UiLoggerFactory;
import com.graphscape.gwt.core.logger.UiLoggerI;

/**
 * @author wuzhen
 * 
 */
public class ErrorReportProxyHandler<T> implements HandlerI<T> {

	private static final UiLoggerI LOG = UiLoggerFactory.getLogger(ErrorReportProxyHandler.class);

	private HandlerI<T> target;

	public ErrorReportProxyHandler(HandlerI<T> target) {
		this.target = target;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.commons.uicore.api.gwt.client.HandlerI#handle(java.lang.Object)
	 */
	@Override
	public void handle(T t) {
		try {
			this.target.handle(t);
		} catch (Throwable e) {
			LOG.error("handler error in handler:" + this.target, e);
			throw UiException.toRtE(e);
		}
	}

}
