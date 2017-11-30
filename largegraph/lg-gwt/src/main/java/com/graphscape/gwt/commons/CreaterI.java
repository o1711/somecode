/**
 *  Jan 31, 2013
 */
package com.graphscape.gwt.commons;

import com.graphscape.gwt.core.ContainerI;

/**
 * @author wuzhen
 * 
 */
public interface CreaterI<T> {

	public T create(ContainerI ct);

}
