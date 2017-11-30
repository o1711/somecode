/**
 * Dec 8, 2013
 */
package com.graphscape.largegraph.core;

import com.graphscape.commons.lang.HandlerI;
import com.graphscape.commons.lang.HasIdI;
import com.graphscape.commons.lang.PropertiesI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public interface ElementI extends PropertiesI<Object>, HasIdI {

	public HandlerI<EventContext> getEventHandler();

	public void setEventHandler(String type);

}
