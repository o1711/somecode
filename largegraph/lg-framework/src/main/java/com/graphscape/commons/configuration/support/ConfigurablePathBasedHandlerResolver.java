/**
 * 
 */
package com.graphscape.commons.configuration.support;

import java.util.List;

import com.graphscape.commons.configuration.ConfigurableI;
import com.graphscape.commons.configuration.ConfigurationI;
import com.graphscape.commons.handling.HandlingContextI;
import com.graphscape.commons.handling.support.PathBasedHandlerResolver;
import com.graphscape.commons.lang.HandlerI;
import com.graphscape.commons.modulization.ComponentFactoryAwareI;
import com.graphscape.commons.modulization.ComponentFactoryI;
import com.graphscape.commons.util.Path;

/**
 * @author wuzhen
 * 
 */
public class ConfigurablePathBasedHandlerResolver<S, X, T extends HandlingContextI<S, X>> extends
		PathBasedHandlerResolver<S, X, T> implements ConfigurableI, ComponentFactoryAwareI {

	private ConfigurationI config;

	private ComponentFactoryI componentFactory;

	@Override
	public void config(ConfigurationI cfg) {

		this.config = cfg;
		List<ConfigurationI> hcL = cfg.getChildConfiguration("handlers").getChildConfigurationList("handler");
		for (ConfigurationI hc : hcL) {
			String pathS = hc.getProperty("path", true);
			HandlerI< T> mh = this.componentFactory.newComponent(hc);
			this.addHandler(Path.valueOf(pathS), mh);
		}

	}

	@Override
	public ConfigurationI getConfiguration() {

		return this.config;
	}

	@Override
	public void setComponentFactory(ComponentFactoryI cf) {
		this.componentFactory = cf;
	}

}
