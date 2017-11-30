/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 2, 2013
 */
package com.graphscape.gwt.commons.event;

import com.graphscape.gwt.commons.frwk.BodyModelI;
import com.graphscape.gwt.core.commons.Path;
import com.graphscape.gwt.core.core.Event;

/**
 * @author wu
 * 
 */
public class BodyItemEvent extends Event {

	public static final Type<BodyItemEvent> TYPE = new Type<BodyItemEvent>("body");

	protected Path path;

	/**
	 * @param type
	 */
	public BodyItemEvent(Type<? extends BodyItemEvent> type, BodyModelI bm, Path path) {
		super(type, bm);
		this.path = path;
	}

	public BodyModelI getModel() {
		return (BodyModelI) this.source;

	}

	public Path getPath() {
		return path;
	}

}
