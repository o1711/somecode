/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 11, 2013
 */
package com.graphscape.gwt.commons.provider.default_;

import com.graphscape.gwt.commons.Constants;
import com.graphscape.gwt.commons.provider.default_.EndpointKeeper;
import com.graphscape.gwt.core.MsgWrapper;
import com.graphscape.gwt.core.ClientI;
import com.graphscape.gwt.core.core.Event.EventHandlerI;
import com.graphscape.gwt.core.endpoint.EndPointI;
import com.graphscape.gwt.core.event.EndpointMessageEvent;
import com.graphscape.gwt.core.event.ScheduleEvent;
import com.graphscape.gwt.core.logger.UiLoggerFactory;
import com.graphscape.gwt.core.logger.UiLoggerI;
import com.graphscape.gwt.core.scheduler.SchedulerI;

/**
 * @author wu
 * @deprecated move to uicore.
 */
public class EndpointKeeper {

	private static final UiLoggerI LOG = UiLoggerFactory.getLogger(EndpointKeeper.class);

	protected EndPointI endpoint;

	private ClientI client;

	private String taskName = "endpoint-keeper";

	public EndpointKeeper(ClientI c) {
		this.endpoint = c.getEndpoint(true);
		this.client = c;

	}

	public void start() {
		this.endpoint.addHandler(EndpointMessageEvent.TYPE, new EventHandlerI<EndpointMessageEvent>() {

			@Override
			public void handle(EndpointMessageEvent t) {
				EndpointKeeper.this.onMessage(t);
			}
		});

		int hbI = this.client.getParameterAsInt(Constants.RK_COMET_HEARTBEATINTERVAL, -1);
		if (hbI < 5 * 1000) {// must longer than 5 second
			LOG.error("parameter of interval for heart beat too short:"+hbI);
			return;
		}
		SchedulerI s = this.endpoint.getContainer().get(SchedulerI.class, true);

		s.scheduleRepeat(taskName, hbI, new EventHandlerI<ScheduleEvent>() {

			@Override
			public void handle(ScheduleEvent t) {
				EndpointKeeper.this.onScheduleEvent(t);
			}
		});// 30S to send ping

	}

	/**
	 * Jan 11, 2013
	 */
	protected void onScheduleEvent(ScheduleEvent t) {
		LOG.info("sending ping request for keeping the endpoint.");
		MsgWrapper req = new MsgWrapper("/ping/ping");
		req.setPayload("text", "keeper");
		this.endpoint.sendMessage(req);
	}

	/**
	 * Jan 11, 2013
	 */
	protected void onMessage(EndpointMessageEvent t) {
		// TODO /ping/success

	}

}
