/**
 * All right is from Author of the file,to be explained in comming days.
 * Sep 22, 2012
 */
package com.graphscape.gwt.core;

import com.graphscape.gwt.core.core.Event;
import com.graphscape.gwt.core.core.UiObjectI;
import com.graphscape.gwt.core.core.Event.EventHandlerI;
import com.graphscape.gwt.core.core.Event.FilterI;
import com.graphscape.gwt.core.core.Event.Type;

/**
 * @author wu
 * 
 */
public interface EventDispatcherI {

	public <E extends Event> void addHandler(UiObjectI src, EventHandlerI<E> l);

	public <E extends Event> void addHandler(UiObjectI src, Type<E> ec,
			EventHandlerI<E> l);

	public <E extends Event> void addHandler(FilterI ef, EventHandlerI<E> eh);

	public void dispatch(Event e);
}
