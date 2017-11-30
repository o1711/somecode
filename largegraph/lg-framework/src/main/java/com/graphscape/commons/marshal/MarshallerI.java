/**
 * Jun 23, 2012
 */
package com.graphscape.commons.marshal;

/**
 * @author wu
 * 
 */
public interface MarshallerI<T> {

	public Object unmarshal(T ser);

	public T marshal(Object ud);

}
