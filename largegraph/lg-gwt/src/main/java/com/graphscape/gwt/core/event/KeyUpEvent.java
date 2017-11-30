/**
 * Jun 29, 2012
 */
package com.graphscape.gwt.core.event;

import com.graphscape.gwt.core.core.UiObjectI;

/**
 * @author wu
 * 
 */
public class KeyUpEvent extends KeyCodeEvent {

	public static final Type<KeyUpEvent> TYPE = new Type<KeyUpEvent>("key-up");

	/** */
	public KeyUpEvent(UiObjectI src, int keyCode, boolean ctlKey) {
		super(TYPE, src, keyCode, ctlKey);
	}

}
