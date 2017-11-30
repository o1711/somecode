/**
 * Jan 22, 2014
 */
package com.graphscape.commons.probject.support;

import com.graphscape.commons.lang.support.ProxyProperties;
import com.graphscape.commons.probject.ProbjectI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class ProxyProbject extends ProxyProperties<Object> implements ProbjectI {

	protected ProbjectI target;

	/**
	 * @param t
	 */
	public ProxyProbject(ProbjectI t) {
		super(t);
		this.target = t;
	}

	@Override
	public String getId() {
		return this.target.getId();
	}

}
