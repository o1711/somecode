/**
 * All right is from Author of the file,to be explained in comming days.
 * Apr 4, 2013
 */
package com.graphscape.gwt.commons.provider.default_.frwk.other;

import com.graphscape.gwt.commons.mvc.simple.LightWeightView;
import com.graphscape.gwt.commons.provider.default_.frwk.other.EndpointBusyIndicator;
import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.core.Event.EventHandlerI;
import com.graphscape.gwt.core.event.EndpointBusyEvent;
import com.graphscape.gwt.core.event.EndpointFreeEvent;

/**
 * @author wu
 * 
 */
public class EndpointBusyIndicator extends LightWeightView {

	/**
	 * @param ctn
	 */
	public EndpointBusyIndicator(ContainerI ctn) {
		super(ctn);
		this.getElement().addClassName("endpoint-busy-indicator");
		this.element.setInnerText("please wait...!");
	}

	/*
	 * Apr 4, 2013
	 */
	@Override
	public void attach() {
		super.attach();
		this.getEndpoint().addHandler(EndpointFreeEvent.TYPE, new EventHandlerI<EndpointFreeEvent>() {

			@Override
			public void handle(EndpointFreeEvent t) {
				EndpointBusyIndicator.this.onEndpointBusy(false);
			}
		});
		this.getEndpoint().addHandler(EndpointBusyEvent.TYPE, new EventHandlerI<EndpointBusyEvent>() {

			@Override
			public void handle(EndpointBusyEvent t) {
				EndpointBusyIndicator.this.onEndpointBusy(true);
			}
		});
	}

	public void onEndpointBusy(boolean busy) {
		this.setVisible(busy);

	}

}
