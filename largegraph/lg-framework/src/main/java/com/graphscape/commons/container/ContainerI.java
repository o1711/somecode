/**
 * 
 */
package com.graphscape.commons.container;

import java.util.List;

import com.graphscape.commons.configuration.ConfigurationProviderI;
import com.graphscape.commons.lang.EnvironmentAwareI;

/**
 * Container is able to hold objects of any type. In general, only important
 * objects will be added into container.
 * 
 * @author wuzhen
 * 
 */
public interface ContainerI extends EnvironmentAwareI {

	public String getId();

	public ContainerI getParent();

	/**
	 * Get the object by Class type.
	 * 
	 * @param <T>
	 * @return object or null
	 */
	<T> T find(Class<T> cls);

	/**
	 * Get the object by Class type.
	 * 
	 * @param <T>
	 * @param class
	 * @param force
	 * @return object or null when parameter force is true; object or throw a
	 *         exception when parameter force is false
	 */
	<T> T find(Class<T> cls, boolean force);

	/**
	 * Get the objects list by Class type.
	 * 
	 * @param <T>
	 * @param class
	 * @return empty list instance will be returned when no match in container
	 */
	<T> List<T> findList(Class<T> cls);

	/**
	 * Add a instance into container
	 * 
	 * @param obj
	 */
	void addObject(Object obj);

	ConfigurationProviderI getConfigurationProvider();

	public List<ContainerI> getChildContainerList();

	public ContainerI getChildContainer(String id, boolean force);

	public void addChild(ContainerI c);

}
