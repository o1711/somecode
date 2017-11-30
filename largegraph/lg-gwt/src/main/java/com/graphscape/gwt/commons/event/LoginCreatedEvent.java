/**
 *  Jan 31, 2013
 */
package com.graphscape.gwt.commons.event;

import com.graphscape.gwt.core.ModelI;
import com.graphscape.gwt.core.event.ModelEvent;

/**
 * @author wuzhen
 * 
 */
public class LoginCreatedEvent extends ModelEvent {

	public static final Type<LoginCreatedEvent> TYPE = new Type<LoginCreatedEvent>("login-created");

	/**
	 * @param src
	 */
	public LoginCreatedEvent(ModelI src) {
		super(TYPE, src);
	}

}
