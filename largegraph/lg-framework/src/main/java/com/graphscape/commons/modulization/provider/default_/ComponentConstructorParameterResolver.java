/**
 * Dec 15, 2013
 */
package com.graphscape.commons.modulization.provider.default_;

import com.graphscape.commons.configuration.ConfigurationI;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.modulization.ComponentFactoryI;
import com.graphscape.commons.modulization.ModuleI;
import com.graphscape.commons.modulization.ParameterResolverI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class ComponentConstructorParameterResolver implements
		ParameterResolverI {

	private ComponentFactoryI componentFactory;

	private ModuleI module;

	public ComponentConstructorParameterResolver(ComponentFactoryI cf) {
		this.componentFactory = cf;
		this.module = cf.getModule();
	}

	@Override
	public Object resolve(ConfigurationI cfg) {

		String factoryId = cfg.getProperty("factory");
		ComponentFactoryI cf = factoryId == null ? this.componentFactory
				: this.componentFactory.findComponentFactory(factoryId, true);

		String id = cfg.getProperty("id");
		if(cf == null){
			throw new GsException("TODO");
		}
		return cf.getComponent(id, true);

	}
}
