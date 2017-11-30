/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 3, 2013
 */
package com.graphscape.gwt.whosegraph.provider.default_.handler.message;

import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.commons.Path;
import com.graphscape.gwt.core.data.message.MessageData;
import com.graphscape.gwt.core.event.EndpointMessageEvent;
import com.graphscape.gwt.whosegraph.event.FailureMessageEvent;
import com.graphscape.gwt.whosegraph.event.SuccessMessageEvent;
import com.graphscape.gwt.whosegraph.provider.default_.handler.support.MHSupport;

/**
 * @author wu
 * 
 */
public class SuccessOrFailureEventMH extends MHSupport {

	/**
	 * @param c
	 */
	public SuccessOrFailureEventMH(ContainerI c) {
		super(c);
	}

	/*
	 * Jan 2, 2013
	 */
	@Override
	public void handle(EndpointMessageEvent t) {
		MessageData res = t.getMessage();
		Path p = res.getPath();
		if ("success".equals(p.getName())) {
			new SuccessMessageEvent(t.getChannel(), res).dispatch();
		} else if ("failure".equals(p.getName())) {
			new FailureMessageEvent(t.getChannel(), res).dispatch();
		}
	}

}
