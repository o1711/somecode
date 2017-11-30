/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 5, 2012
 */
package com.graphscape.gwt.core;

import com.google.gwt.user.client.Window;
import com.graphscape.gwt.core.commons.Size;
import com.graphscape.gwt.core.core.UiObjectI;
import com.graphscape.gwt.core.core.Event.EventHandlerI;
import com.graphscape.gwt.core.event.ScrollEvent;
import com.graphscape.gwt.core.state.State;
import com.graphscape.gwt.core.state.StatefulI;

/**
 * @author wu
 * @see Window in gwt.
 *      <p>
 *      this is singleton .
 */
public interface WindowI extends UiObjectI, StatefulI {

	public static final State CLOSING = State.valueOf("CLOSING");

	public void resizeTo(Size size);

	public Size getSize();

	public void addScrollHandler(EventHandlerI<ScrollEvent> hdl);

}
