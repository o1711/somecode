/**
 * Dec 19, 2013
 */
package com.graphscape.largegraph.console.provider.default_.cli.handlers;

import java.util.List;

import com.graphscape.commons.cli.CliHandlerI;
import com.graphscape.commons.configuration.ConfigurableI;
import com.graphscape.commons.configuration.ConfigurationI;
import com.graphscape.commons.modulization.ComponentFactoryAwareI;
import com.graphscape.commons.modulization.ComponentFactoryI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class ConfigurableDispatchingCliHandler extends DispatchingCliHandler implements ConfigurableI,
		ComponentFactoryAwareI {

	private ComponentFactoryI componentFactory;

	private ConfigurationI config;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.commons.configuration.ConfigurableI#config(com.graphscape
	 * .commons.configuration.ConfigurationI)
	 */
	@Override
	public void config(ConfigurationI cfg) {
		this.config = cfg;
		// handlers
		List<ConfigurationI> hcfgL = this.config.getChildConfiguration("handlers").getChildConfigurationList(
				"handler");

		for (ConfigurationI hcfg : hcfgL) {
			CliHandlerI hdl = this.componentFactory.newComponent(hcfg);
			String cmd = hcfg.getProperty("command", true);
			this.addCliHandler(cmd, hdl);

		}
	}

	@Override
	public ConfigurationI getConfiguration() {

		return this.config;
	}

	@Override
	public void setComponentFactory(ComponentFactoryI componentFatory) {
		this.componentFactory = componentFatory;
	}

}
