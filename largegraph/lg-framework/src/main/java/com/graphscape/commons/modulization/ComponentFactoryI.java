/**
 * 
 */
package com.graphscape.commons.modulization;

import java.util.List;

import com.graphscape.commons.configuration.ConfigId;
import com.graphscape.commons.configuration.ConfigurationI;

/**
 * @author wu
 * 
 */
public interface ComponentFactoryI {

	public String getId();

	public ModuleI getModule();

	public ComponentFactoryI getParentComponentFactory();

	public <T> Class<T> getComponentClass(String key, boolean force);

	public <T> void addComponentClass(String key, Class<T> cls);

	public <T> void addComponentClass(String[] keys, Class<T> cls);

	public <T> void addComponentClass(String key, Class<T> cls, boolean replace);

	public <T> T newComponent(ConfigurationI cfg);

	public <T> T newComponentByChild(ConfigurationI cfg, Class<T> itf,
			boolean force);

	public <T> T newComponent(ConfigurationI cfg, Class<T> cls);

	public <T> T newComponent(ConfigId cfgId);

	public <T> T getComponent(String id, boolean force);

	public List<ComponentFactoryI> getChildComponentFactoryList();

	public ComponentFactoryI findComponentFactory(String id, boolean force);

	public ComponentFactoryI findComponentFactoryDownward(String id,
			boolean force);

	@Deprecated
	public <T> T newComponent(Class<T> cls);

}
