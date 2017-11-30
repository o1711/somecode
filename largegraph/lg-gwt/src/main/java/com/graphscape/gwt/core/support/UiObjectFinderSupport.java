/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 14, 2012
 */
package com.graphscape.gwt.core.support;

import java.util.List;

import com.graphscape.gwt.core.UiException;
import com.graphscape.gwt.core.UiObjectFinderI;
import com.graphscape.gwt.core.core.UiObjectI;

/**
 * @author wu
 * 
 */
public class UiObjectFinderSupport<T> implements UiObjectFinderI<T> {

	protected UiObjectI startPoint;
	
	@Override
	public List<T> findList() {
		throw new UiException("TODO");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.commons.uicore.api.gwt.client.UiObjectFinderI#findSingle()
	 */
	@Override
	public T findSingle(boolean force) {
		List<T> rtl = this.findList();
		if (rtl.isEmpty()) {
			if (force) {
				throw new UiException("force by finder:" + this);
			} else {
				return null;
			}
		} else if (rtl.size() == 1) {
			return rtl.get(0);
		} else {
			throw new UiException("multiple found by finder:" + this);
		}
	}

}
