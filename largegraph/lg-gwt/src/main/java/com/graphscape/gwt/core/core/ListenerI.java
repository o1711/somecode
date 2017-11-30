/**
 * Jun 13, 2012
 */
package com.graphscape.gwt.core.core;

import com.graphscape.gwt.core.core.Event;

/**
 * @author wuzhen
 * 
 */
public interface ListenerI<E extends Event> {

	public void onEvent(E e);

}
