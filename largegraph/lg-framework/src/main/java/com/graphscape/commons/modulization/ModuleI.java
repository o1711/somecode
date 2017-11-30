/**
 * 
 */
package com.graphscape.commons.modulization;

import java.util.List;

import com.graphscape.commons.configuration.ConfigId;
import com.graphscape.commons.configuration.ConfigurationProviderI;
import com.graphscape.commons.container.ContainerI;
import com.graphscape.commons.lang.EnvironmentAwareI;
import com.graphscape.commons.lang.PropertiesI;

/**
 * 
 * @author wuzhen
 * 
 */
public interface ModuleI extends EnvironmentAwareI {

	public ApplicationI getApplication();
	
	public List<String> getDependencyModuleList();

	public String getId();

	public ModuleI getParentModule();

	public void active(ModuleContext pc);

	public void deactive(ModuleContext pc);

	public PropertiesI<String> getProperties();
	
	public ModuleI findModule(String id,boolean force);

	ModuleI activeChildModule(ConfigId childCfgId);

	ModuleI[] activeChildModules(ConfigId[] childCfgIds);

	List<ModuleI> getChildModuleList();

	ModuleI getChildModule(String id, boolean force);

	ContainerI getContainer();

	ConfigurationProviderI getConfigurationProvider();

	public ComponentFactoryI getComponentFactory();

}
