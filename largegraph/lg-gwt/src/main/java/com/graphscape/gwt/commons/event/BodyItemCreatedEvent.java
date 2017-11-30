/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 2, 2013
 */
package com.graphscape.gwt.commons.event;

import com.graphscape.gwt.commons.frwk.BodyModelI;
import com.graphscape.gwt.core.commons.Path;

/**
 * @author wu
 * 
 */
public class BodyItemCreatedEvent extends BodyItemEvent {

	public static final Type<BodyItemCreatedEvent> TYPE = new Type<BodyItemCreatedEvent>(BodyItemEvent.TYPE,
			"created");

	/**
	 * @param type
	 */
	public BodyItemCreatedEvent(BodyModelI bm, Path path) {
		super(TYPE, bm, path);
	}

}
