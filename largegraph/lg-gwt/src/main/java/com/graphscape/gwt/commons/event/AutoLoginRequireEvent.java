/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 11, 2012
 */
package com.graphscape.gwt.commons.event;

import com.graphscape.gwt.core.core.Event;
import com.graphscape.gwt.core.core.UiObjectI;

/**
 * @author wu
 * 
 * 
 */
public class AutoLoginRequireEvent extends Event {

	public static final Type<AutoLoginRequireEvent> TYPE = new Type<AutoLoginRequireEvent>("auto-login-request");

	/**
	 * @param src
	 * @param code
	 */
	public AutoLoginRequireEvent(UiObjectI src) {
		super(TYPE, src);
	}

}
