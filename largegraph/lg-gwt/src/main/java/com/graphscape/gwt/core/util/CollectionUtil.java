/**
 * Jul 1, 2012
 */
package com.graphscape.gwt.core.util;

import java.util.Collection;

import com.graphscape.gwt.core.UiException;

/**
 * @author wu
 * 
 */
public class CollectionUtil {

	public static <T> T single(Collection<T> cs, boolean force) {
		if (cs.isEmpty()) {
			if (force) {
				throw new UiException("force:");
			} else {
				return null;
			}
		} else if (cs.size() > 1) {
			throw new UiException("duplicated:" + cs);
		} else {
			return cs.iterator().next();
		}
	}
}
