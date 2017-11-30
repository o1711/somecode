/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 14, 2012
 */
package com.graphscape.gwt.core;

import java.util.List;

/**
 * @author wu
 * 
 */
public interface UiObjectFinderI<T> {

	public List<T> findList();
	
	public T findSingle(boolean force);
	

}
