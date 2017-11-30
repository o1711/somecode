/**
 * 
 */
package com.graphscape.commons.container.provider.default_;

import com.graphscape.commons.container.ContainerI;
import com.graphscape.commons.container.ContainerSupport;
import com.graphscape.commons.lang.EnvironmentI;

/**
 * @author wuzhen
 * 
 */
public class DefaultContainer extends ContainerSupport {

	public DefaultContainer(EnvironmentI env, String id) {
		super(env, id);
	}

	/**
	 * @param id
	 */
	public DefaultContainer(ContainerI parent, String id) {
		super(parent, id);
	}

}
