/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 17, 2012
 */
package com.graphscape.gwt.core.support;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.graphscape.gwt.core.GwtHandlerI;
import com.graphscape.gwt.core.logger.UiLoggerFactory;
import com.graphscape.gwt.core.logger.UiLoggerI;

/**
 * @author wu
 * 
 */
public abstract class GwtHandlerSupport<E extends GwtEvent<H>, H extends EventHandler> 
		implements EventHandler,GwtHandlerI<E,H> {

	protected UiLoggerI LOG = UiLoggerFactory
			.getLogger(GwtHandlerSupport.class);

	protected void handle(E evt) {
		try {
			this.handleInternal(evt);
		} catch (Throwable t) {
			LOG.error("", t);
		}
	}

	public H cast() {
		return (H) this;
	}

	protected abstract void handleInternal(E evt);

}
