/**
 * Jul 1, 2012
 */
package com.graphscape.gwt.core;

import java.util.List;

import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.EventBusI;

/**
 * @author wu
 * 
 */
public interface ContainerI {

	public static interface AwareI {
		public void init(ContainerI c);
	}

	public <T> List<T> getList(Class<T> cls);

	public <T> T get(Class<T> cls, boolean force);

	public void add(Object obj);

	public EventBusI getEventBus();

}
