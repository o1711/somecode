package com.graphscape.commons.configuration;

import java.util.List;

import com.graphscape.commons.lang.Enumeration;
import com.graphscape.commons.lang.PropertiesI;

/**
 * 
 * @author wuzhen0808@gmail.com
 * 
 */
public interface ConfigurationI extends PropertiesI<String> {

	public static class Type extends Enumeration {

		/**
		 * @param name
		 */
		public Type(String name) {
			super(name);
		}

		public static Type valueOf(String vs) {
			return new Type(vs);
		}
	}

	public static Type ROOT = Type.valueOf("config");

	public Type getType();

	public ConfigurationI getThisOrReferencedConfiguration();

	public ConfigurationI getChildConfiguration(String name);

	public ConfigurationI getChildConfiguration(String name, boolean force);

	public ConfigurationI getChildOrReferencedConfiguration(String name);

	public ConfigurationI getChildOrReferencedConfiguration(String name,
			boolean force);

	public List<ConfigurationI> getGrandsonListByChildOrReferencedConfiguration(
			String name);

	public ConfigurationI getChildConfiguration(String name, int index);

	public List<ConfigurationI> getChildConfigurationList(String type);
	
	public List<ConfigurationI> getChildConfigurationList(Type type);

	public List<ConfigurationI> getChildConfigurationList();

	public ConfigId getId();

	public <T> T getPropertyAsNewInstance(String key);

	public <T> T getPropertyAsNewInstance(String key, Class[] clss,
			Object[] args);

	public <T> T getPropertyAsNewInstance(String key, boolean force);

	public <T> T getPropertyAsNewInstance(String key, Class[] clss,
			Object[] args, boolean force);

	public List<String> getPropertyAsCSV(String key);

	public <T> Class<T> getPropertyAsClass(String key);

	public <T> Class<T> getPropertyAsClass(String key, boolean force);
	
	public ConfigurationI getPropertyAsConfiguration(String key, boolean force);

}
