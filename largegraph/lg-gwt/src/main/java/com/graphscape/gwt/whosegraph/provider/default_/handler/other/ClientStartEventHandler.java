/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 13, 2013
 */
package com.graphscape.gwt.whosegraph.provider.default_.handler.other;

import com.graphscape.gwt.commons.frwk.BottomViewI;
import com.graphscape.gwt.commons.frwk.FrwkControlI;
import com.graphscape.gwt.commons.mvc.support.UiHandlerSupport;
import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.ClientI;
import com.graphscape.gwt.core.commons.Path;
import com.graphscape.gwt.core.core.Event.EventHandlerI;
import com.graphscape.gwt.core.endpoint.EndPointI;
import com.graphscape.gwt.core.event.AfterClientStartEvent;
import com.graphscape.gwt.whosegraph.HeaderNames;
import com.graphscape.gwt.whosegraph.provider.default_.handler.message.ModuleListRefreshMH;
import com.graphscape.gwt.whosegraph.provider.default_.handler.message.SuccessOrFailureEventMH;

/**
 * @author wu
 * 
 */
public class ClientStartEventHandler extends UiHandlerSupport implements EventHandlerI<AfterClientStartEvent> {

	/**
	 * @param c
	 */
	public ClientStartEventHandler(ContainerI c) {
		super(c);
	}

	/*
	 * Jan 13, 2013
	 */
	@Override
	public void handle(AfterClientStartEvent t) {
		this.activeMessageHandlers(this.container, t.getClient());//
		FrwkControlI fc = this.getControl(FrwkControlI.class, true);
		//
		fc.addHeaderItem(HeaderNames.H1_LOGO, true);//left
		// right
		fc.addHeaderItemIfNotExist(HeaderNames.H1_MODULE_LIST);
		//
		BottomViewI bv = fc.getBottomView();
		//bv.addItem(HeaderNames.H1_ABOUT);
		
	}
	
	private void activeMessageHandlers(ContainerI c, ClientI client) {
		EndPointI dis = client.getEndpoint(true);
		dis.addHandler(Path.valueOf("/endpoint/message"), new SuccessOrFailureEventMH(c));
		
		// exp
		dis.addHandler(Path.valueOf("/endpoint/message/module-list/refresh/success"), new ModuleListRefreshMH(c));// refresh
		
	}


}
