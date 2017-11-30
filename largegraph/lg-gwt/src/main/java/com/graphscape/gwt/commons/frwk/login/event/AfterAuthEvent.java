/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 23, 2012
 */
package com.graphscape.gwt.commons.frwk.login.event;

import com.graphscape.gwt.core.core.Event;
import com.graphscape.gwt.core.core.UiObjectI;

/**
 * @author wu
 * 
 */
public class AfterAuthEvent extends Event {

	public static final Type<AfterAuthEvent> TYPE = new Type<AfterAuthEvent>("unknow");

	protected String sessionId;
	
	/**
	 * @param type
	 */
	public AfterAuthEvent(UiObjectI src,String sessionId) {
		super(TYPE, src);
		this.sessionId = sessionId;
	}

	/**
	 * @return the sessionId
	 */
	public String getSessionId() {
		return sessionId;
	}
	
}
