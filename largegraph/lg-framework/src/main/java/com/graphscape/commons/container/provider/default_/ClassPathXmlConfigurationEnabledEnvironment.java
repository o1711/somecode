/**
 * 
 */
package com.graphscape.commons.container.provider.default_;

import java.util.Map;

import com.graphscape.commons.configuration.ConfigurationI;
import com.graphscape.commons.configuration.ConfigurationProviderI;
import com.graphscape.commons.configuration.provider.xml.XmlConfigurationProvider;
import com.graphscape.commons.lang.support.EnvironmentSupport;

/**
 * @author wuzhen
 * 
 */
public class ClassPathXmlConfigurationEnabledEnvironment extends EnvironmentSupport {

	protected ConfigurationI config;

	public ClassPathXmlConfigurationEnabledEnvironment() {
		this(null, null);
	}

	public ClassPathXmlConfigurationEnabledEnvironment(Map<String, String> vars) {
		this(null, vars);
	}

	public ClassPathXmlConfigurationEnabledEnvironment(ConfigurationI cfg) {
		this(cfg, null);
	}

	public ClassPathXmlConfigurationEnabledEnvironment(ConfigurationI cfg, Map<String, String> vars) {
		this.config = cfg;
		if (vars != null) {
			this.varMap.putAll(vars);
		}

		this.addEnvironmentService(ConfigurationProviderI.class, new XmlConfigurationProvider(this));

	}

	public ConfigurationI getConfiguration() {
		return this.config;
	}
}
