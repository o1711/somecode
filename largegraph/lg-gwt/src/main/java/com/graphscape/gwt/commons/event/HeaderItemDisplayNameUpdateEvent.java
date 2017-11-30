/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 2, 2013
 */
package com.graphscape.gwt.commons.event;

import com.graphscape.gwt.commons.frwk.HeaderModelI;
import com.graphscape.gwt.core.event.ModelUpdateEvent;

/**
 * @author wu
 * @deprecated remove
 */
public class HeaderItemDisplayNameUpdateEvent extends ModelUpdateEvent {

	public static final Type<HeaderItemDisplayNameUpdateEvent> TYPE = new Type<HeaderItemDisplayNameUpdateEvent>(
			ModelUpdateEvent.TYPE, "header-item-display-name");

	/**
	 * @param type
	 */
	public HeaderItemDisplayNameUpdateEvent(HeaderModelI.ItemModel hi, String dname) {
		super(TYPE, hi, "displayName", dname);
	}

}
