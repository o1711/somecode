/**
 * Jan 6, 2014
 */
package com.graphscape.commons.record;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public interface SerializerI<T> {

	public byte[] serialize(T t);

	public T deserialize(byte[] content);

}
