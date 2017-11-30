/**
 * Dec 8, 2013
 */
package com.graphscape.commons.modulization.provider.default_;

import com.graphscape.commons.container.ContainerI;
import com.graphscape.commons.modulization.ObjectManagerI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class ContainerObjectManager implements ObjectManagerI {

	private ContainerI container;

	public ContainerObjectManager(ContainerI container) {
		this.container = container;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.commons.modulization.ObjectManagerI#manage(java.lang.Object
	 * )
	 */
	@Override
	public void manage(Object obj) {
		this.container.addObject(obj);

	}

}
