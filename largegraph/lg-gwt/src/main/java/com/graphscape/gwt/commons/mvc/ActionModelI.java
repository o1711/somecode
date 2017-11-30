/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 11, 2012
 */
package com.graphscape.gwt.commons.mvc;

import com.graphscape.gwt.core.ModelI;
import com.graphscape.gwt.core.commons.Path;
import com.graphscape.gwt.core.core.Event.EventHandlerI;
import com.graphscape.gwt.core.data.ErrorInfosData;
import com.graphscape.gwt.core.event.ModelValueEvent;
import com.graphscape.gwt.core.state.State;

/**
 * @author wu
 * @deprecated todo remove this model def
 */
public interface ActionModelI extends ModelI {

	public static final Location L_LOCAL = Location.valueOf("_local");

	public static final Location L_STATE = Location.valueOf("_state");

	public static final Location L_HIDDEN = Location.valueOf("_hidden");

	public static final State TRIGGERED = State.valueOf("triggered");

	public static final State PROCESSING = State.valueOf("processing");

	public static final State PROCESSED = State.valueOf("processed");

	public static final State NORMAL = State.valueOf("normal");

	public void trigger();

	public State getState();

	public void setState(State st);

	public ModelI getControlModel();
	
	public Path getActionPath();

	public void addTriggerHandler(EventHandlerI<ModelValueEvent> ah);

	public void addProcessedHandler(EventHandlerI<ModelValueEvent> ah);

	public void processed(ErrorInfosData eis);

	public ErrorInfosData getErrorInfos();

	public void setHidden(boolean hidden);

}
