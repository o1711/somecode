/**
 * Jun 29, 2012
 */
package com.graphscape.gwt.core.event;

import com.google.gwt.event.dom.client.KeyCodes;
import com.graphscape.gwt.core.core.Event;
import com.graphscape.gwt.core.core.UiObjectI;

/**
 * @author wu
 * 
 */
public abstract class KeyCodeEvent extends Event {

	protected int keyCode;
	
	protected boolean ctlKey;
	/** */
	public KeyCodeEvent(Type<? extends KeyCodeEvent> type,UiObjectI src, int keyCode, boolean ctlKey) {
		super(type, src);
		this.keyCode = keyCode;
		this.ctlKey = ctlKey;
	}
	
	public int getNativeKeyCode() {
		return this.keyCode;
	}
	
	public boolean isEnter(){
		return KeyCodes.KEY_ENTER == this.keyCode;
	}
	
	public boolean isCtlKey(){
		return this.ctlKey;
	}

}
