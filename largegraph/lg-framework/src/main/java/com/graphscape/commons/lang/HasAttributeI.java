/**
 *  
 */
package com.graphscape.commons.lang;

/**
 * @author wu
 * 
 */
public interface HasAttributeI {

	<T> T getAttribute(String key);

	<T> T getAttribute(String key, T def);
	
	<T> T getAttribute(String key, boolean force);

	<T> T setAttribute(String key, T t);
	
	public void setAttributes(PropertiesI<Object> pts);

}
