/**
 * Dec 8, 2013
 */
package com.graphscape.commons.handling;

import com.graphscape.commons.lang.ErrorInfos;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public interface HandlingContextI<S, T> {

	public S getInputData();

	public T getOutputData();

	public ErrorInfos getErrorInfos();
}
