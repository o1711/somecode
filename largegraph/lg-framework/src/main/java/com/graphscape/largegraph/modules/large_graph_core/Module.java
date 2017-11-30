/**
 * 2013 ����10:28:03
 */
package com.graphscape.largegraph.modules.large_graph_core;

import com.graphscape.commons.configuration.ConfigId;
import com.graphscape.commons.container.ContainerI;
import com.graphscape.commons.modulization.ComponentFactoryI;
import com.graphscape.commons.modulization.ModuleContext;
import com.graphscape.commons.modulization.ModuleI;
import com.graphscape.commons.modulization.support.ModuleSupport;
import com.graphscape.largegraph.core.GraphI;
import com.graphscape.largegraph.core.provider.blueprint.DefaultLargeGraph;

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

		ContainerI c = mc.getContainer();
		ComponentFactoryI cf = c.find(ComponentFactoryI.class, true);
		DefaultLargeGraph lg = (DefaultLargeGraph) mc.getContainer().find(
				GraphI.class);
	}

	@Override
	protected void deactiveInternal(ModuleContext mc) {

	}

}
