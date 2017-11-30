/**
 * Dec 15, 2013
 */
package com.graphscape.largegraph.modules.large_graph_server;

import com.graphscape.commons.configuration.ConfigId;
import com.graphscape.commons.modulization.ModuleContext;
import com.graphscape.commons.modulization.ModuleI;
import com.graphscape.commons.modulization.support.ModuleSupport;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class Module extends ModuleSupport {

	/**
	 * @param userCfgId
	 * @param env
	 */
	public Module(ConfigId id, ModuleI parent) {
		super(id, parent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.commons.modulization.support.ModuleSupport#activeInternal
	 * (com.graphscape.commons.modulization.ModuleContext)
	 */
	@Override
	protected void activeInternal(ModuleContext mc) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.commons.modulization.support.ModuleSupport#deactiveInternal
	 * (com.graphscape.commons.modulization.ModuleContext)
	 */
	@Override
	protected void deactiveInternal(ModuleContext mc) {
		// TODO Auto-generated method stub

	}

}
