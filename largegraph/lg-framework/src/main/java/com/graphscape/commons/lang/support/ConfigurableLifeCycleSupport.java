package com.graphscape.commons.lang.support;

import com.graphscape.commons.configuration.ConfigurableI;
import com.graphscape.commons.configuration.ConfigurationI;

public abstract class ConfigurableLifeCycleSupport extends LifeCycleSupport implements ConfigurableI {

	protected ConfigurationI config;

	@Override
	public void config(ConfigurationI cfg) {
		this.config = cfg;
	}

	@Override
	public ConfigurationI getConfiguration() {
		return this.config;
	}
}
