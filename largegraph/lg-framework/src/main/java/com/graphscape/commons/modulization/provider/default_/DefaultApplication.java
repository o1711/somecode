/**
 * 
 */
package com.graphscape.commons.modulization.provider.default_;

import com.graphscape.commons.configuration.ConfigId;
import com.graphscape.commons.lang.EnvironmentI;
import com.graphscape.commons.lang.support.EnvironmentAwareLifeCycleSupport;
import com.graphscape.commons.modulization.ApplicationI;
import com.graphscape.commons.modulization.ModuleContext;
import com.graphscape.commons.modulization.ModuleI;

/**
 * @author wuzhen
 * 
 */
public class DefaultApplication extends EnvironmentAwareLifeCycleSupport implements ApplicationI {

	private ModuleI rootModule;

	private EnvironmentI env;

	protected ConfigId configId;

	public DefaultApplication(EnvironmentI env, ConfigId cfgId) {
		this.env = env;

		this.configId = cfgId;
	}

	@Override
	public void doStart() {

		this.rootModule = new DefaultRootModule(this.configId.getChild("container"), this);
		ModuleContext mc = new ModuleContext(rootModule);
		rootModule.active(mc);

	}

	@Override
	public void doShutdown() {

	}

	public ModuleI getRootModule() {
		return this.rootModule;
	}

	public String getId() {
		return this.configId.toString();
	}

	@Override
	public EnvironmentI getEnvironment() {
		return this.env;
	}

	@Override
	public ModuleI getModule(String id, boolean force) {
		ModuleI rt = this.rootModule.findModule(id,force);
		
		return rt;
	}
}
