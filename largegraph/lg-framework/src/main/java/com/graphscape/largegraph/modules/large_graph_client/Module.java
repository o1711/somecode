/**
 * 2013 ����10:28:03
 */
package com.graphscape.largegraph.modules.large_graph_client;

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
	 * @param id
	 */
	public Module(ConfigId id, ModuleI parent) {
		super(id, parent);
	}

	@Override
	protected void activeInternal(ModuleContext mc) {

	}

	@Override
	protected void deactiveInternal(ModuleContext mc) {

	}

}
