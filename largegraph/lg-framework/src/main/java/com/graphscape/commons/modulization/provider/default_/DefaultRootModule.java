/**
 * 
 */
package com.graphscape.commons.modulization.provider.default_;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.graphscape.commons.configuration.ConfigId;
import com.graphscape.commons.modulization.ApplicationI;
import com.graphscape.commons.modulization.ModuleContext;
import com.graphscape.commons.modulization.ModuleI;
import com.graphscape.commons.modulization.support.ModuleSupport;

/**
 * @author wuzhen
 * 
 */
public class DefaultRootModule extends ModuleSupport {

	private static Logger LOG = LoggerFactory.getLogger(DefaultRootModule.class);

	public DefaultRootModule(ConfigId cfgId, ApplicationI env) {
		super(cfgId, env);
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.commons.modulization.support.ModuleSupport#activeInternal
	 * (com.graphscape.commons.modulization.ModuleContext,
	 * com.graphscape.commons.container.ContainerI,
	 * com.graphscape.commons.modulization.ModulizerI)
	 */
	@Override
	protected void activeInternal(ModuleContext ac) {
		

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.commons.modulization.support.ModuleSupport#deactiveInternal
	 * (com.graphscape.commons.modulization.ModuleContext,
	 * com.graphscape.commons.container.ContainerI,
	 * com.graphscape.commons.modulization.ModulizerI)
	 */
	@Override
	protected void deactiveInternal(ModuleContext ac) {
		LOG.info("shutdown");

		for (int i = this.moduleIdList.size(); i > 0; i--) {
			String id = this.moduleIdList.get(i - 1);
			ModuleI pl = this.getChildModule(id);
			ModuleContext mcI = new ModuleContext(pl);
			pl.deactive(mcI);
		}
	}



}
