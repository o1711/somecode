/**
 */
package com.graphscape.commons.lang;

import java.util.List;
import java.util.Map;

/**
 * @author wu
 * 
 */
public interface PropertiesI<T> {
	void setProperty(String key, T value);

	<X extends T> void setProperty(Map.Entry<String, X> entry);

	T getProperty(String key);

	T getProperty(String key, boolean force);

	<X extends T> X getProperty(String key, X def);

	int getPropertyAsInt(String key, int def);

	boolean getPropertyAsBoolean(String key, boolean def);

	List<String> keyList();

	<X extends T> void setProperties(Map<String, X> map);

	void setPropertiesByArray(Object... keyValues);

	<X extends T> void setProperties(PropertiesI<X> pts);

	Map<String, T> getAsMap();

	PropertiesI<T> mergeFrom(PropertiesI<T> pts);

	PropertiesI<T> copy();
}
