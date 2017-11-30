/**
 * 
 */
package com.graphscape.commons.configuration.support;

import com.graphscape.commons.configuration.ConfigurableI;
import com.graphscape.commons.configuration.ConfigurationI;

/**
 * @author wuzhen
 * 
 */
public class ConfigurableSupport implements ConfigurableI {

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
