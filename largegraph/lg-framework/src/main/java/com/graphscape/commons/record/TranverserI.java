/**
 * Jan 11, 2014
 */
package com.graphscape.commons.record;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public interface TranverserI<T extends SegmentI> {
	public void start(T segment);
}
