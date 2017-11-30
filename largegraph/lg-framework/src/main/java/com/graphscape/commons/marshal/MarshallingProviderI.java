/**
 * 
 */
package com.graphscape.commons.marshal;

/**
 * @author wuzhen
 * 
 */
public interface MarshallingProviderI<T> {
	public MarshallerI<T> getMarshaller(Class<?> dataCls);

	public MarshallerI<T> getMarshaller(Class<?> dataCls, boolean force);

	public MarshallerI<T> getMarshaller(String type);
}
