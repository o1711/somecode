/**
 * Jun 29, 2012
 */
package com.graphscape.gwt.commons.widget.basic;

import com.graphscape.gwt.commons.widget.BasicI;
import com.graphscape.gwt.core.state.State;

/**
 * @author wu
 * 
 */
public interface ButtonI extends BasicI {

	public static State DOWN = State.valueOf("down");

	public static State UP = State.valueOf("up");

	public State getState();

	public void setText(boolean toloc, String text);

	public void disable(boolean dis);
	
	public void click();

}
