/**
 * Dec 15, 2013
 */
package com.graphscape.commons.container.support;

import com.graphscape.commons.configuration.support.ConfigurableSupport;
import com.graphscape.commons.container.ContainerAwareI;
import com.graphscape.commons.container.ContainerI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class ContainerAwareConfigurableSupport extends ConfigurableSupport
		implements ContainerAwareI {

	protected ContainerI container;

	@Override
	public void setContainer(ContainerI c) {
		this.container = c;
	}

	@Override
	public ContainerI getContainer() {
		return this.container;
	}

}
