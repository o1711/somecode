/**
 * Dec 21, 2013
 */
package com.graphscape.commons.lang.support;

import com.graphscape.commons.lang.EnvironmentAwareI;
import com.graphscape.commons.lang.EnvironmentI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public abstract class EnvironmentAwareLifeCycleSupport extends LifeCycleSupport implements EnvironmentAwareI {

	protected EnvironmentI envrionment;

	@Override
	public EnvironmentI getEnvironment() {
		return this.envrionment;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.commons.lang.EnvironmentAwareI#setEnvironment(com.graphscape
	 * .commons.lang.EnvironmentI)
	 */
	@Override
	public void setEnvironment(EnvironmentI env) {
		this.envrionment = env;
	}

}
