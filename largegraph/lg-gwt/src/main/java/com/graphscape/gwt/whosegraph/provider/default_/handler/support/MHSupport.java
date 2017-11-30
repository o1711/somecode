/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 3, 2013
 */
package com.graphscape.gwt.whosegraph.provider.default_.handler.support;

import com.graphscape.gwt.commons.mvc.support.UiHandlerSupport;
import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.event.EndpointMessageEvent;
import com.graphscape.gwt.core.message.MessageHandlerI;
import com.graphscape.gwt.whosegraph.MainControlI;

/**
 * @author wu
 * 
 */
public abstract class MHSupport extends UiHandlerSupport implements MessageHandlerI<EndpointMessageEvent> {

	/**
	 * @param c
	 */
	public MHSupport(ContainerI c) {
		super(c);
	}

	protected MainControlI getMainControl(){
		return this.getControl(MainControlI.class, true);
	}
	
}
