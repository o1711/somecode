/**
 *  Jan 31, 2013
 */
package com.fs.uiclient.impl.gwt.client.handler.other;

import com.fs.uiclient.api.gwt.client.main.MainControlI;
import com.fs.uicommons.api.gwt.client.event.HeaderItemEvent;
import com.fs.uicommons.api.gwt.client.mvc.support.UiHandlerSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;

/**
 * @author wuzhen
 * 
 */
public class ProfileHeaderItemHandler extends UiHandlerSupport implements EventHandlerI<HeaderItemEvent> {

	/**
	 * @param c
	 */
	public ProfileHeaderItemHandler(ContainerI c) {
		super(c);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicore.api.gwt.client.HandlerI#handle(java.lang.Object)
	 */
	@Override
	public void handle(HeaderItemEvent t) {

		MainControlI mc = this.getControl(MainControlI.class, true);
		mc.openProfile();
		
	}

}
