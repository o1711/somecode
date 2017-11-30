/**
 * 
 */
package com.graphscape.commons.modulization;

import com.graphscape.commons.container.ContainerI;

/**
 * @author wuzhen
 * 
 */
public class ModuleContext {
	//parent module
	private ModuleI module;

	public ModuleContext(ModuleI module) {
		this.module = module;

	}

	/**
	 * @return the module
	 */
	public ModuleI getModule() {
		return module;
	}

	public ContainerI getContainer() {
		return this.module.getContainer();
	}

}
