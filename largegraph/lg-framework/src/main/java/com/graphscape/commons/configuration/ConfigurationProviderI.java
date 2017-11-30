package com.graphscape.commons.configuration;


public interface ConfigurationProviderI {

	void add(ConfigurationI cfg);

	ConfigurationI getConfiguration(ConfigId id);
	
	ConfigurationI getConfiguration(ConfigId id, boolean force);
	
}
