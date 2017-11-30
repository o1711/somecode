/**
 *  Jan 31, 2013
 */
package com.graphscape.gwt.commons.provider.default_.handler;

import com.graphscape.gwt.commons.mvc.support.UiHandlerSupport;
import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.core.Event.EventHandlerI;
import com.graphscape.gwt.core.event.EndpointBondEvent;

/**
 * @author wuzhen
 * 
 */
public class EndpointCloseHandler extends UiHandlerSupport implements EventHandlerI<EndpointBondEvent> {

	/**
	 * @param c
	 */
	public EndpointCloseHandler(ContainerI c) {
		super(c);
	}

	@Override
	public void handle(EndpointBondEvent e) {

	}
}
