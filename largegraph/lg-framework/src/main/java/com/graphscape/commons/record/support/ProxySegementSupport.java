/**
 * Jan 4, 2014
 */
package com.graphscape.commons.record.support;

import com.graphscape.commons.file.PlainRegionI;
import com.graphscape.commons.record.SegmentI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public abstract class ProxySegementSupport<T extends SegmentI> extends SegmentSupport<T> implements SegmentI {
	protected T target;

	public ProxySegementSupport(T target) {
		super(target);
		this.target = target;
	}

	@Override
	public void open() {
		this.target.open();

	}

	@Override
	public void close() {
		this.target.close();
	}

	@Override
	public void clear() {
		this.target.clear();
	}

}
