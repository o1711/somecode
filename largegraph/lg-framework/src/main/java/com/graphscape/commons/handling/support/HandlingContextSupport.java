/**
 * Dec 8, 2013
 */
package com.graphscape.commons.handling.support;

import com.graphscape.commons.handling.HandlingContextI;
import com.graphscape.commons.lang.HasPathI;
import com.graphscape.commons.lang.support.HasAttributeSupport;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public abstract class HandlingContextSupport<S, T> extends HasAttributeSupport implements
		HandlingContextI<S, T>, HasPathI {

	protected T output;

	protected S input;

	public HandlingContextSupport(S msg, T out) {
		this.input = msg;
		this.output = out;
	}

	@Override
	public S getInputData() {
		return input;
	}

	@Override
	public T getOutputData() {
		return this.output;
	}

}
