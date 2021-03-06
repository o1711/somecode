/**
 * Jun 19, 2012
 */
package com.fs.commons.api.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fs.commons.api.lang.FsException;

/**
 * @author wu
 * 
 */
public class CollectionUtil {

	public static <T> List<T> toList(Collection<T> cl) {

		List<T> rt = new ArrayList<T>();
		rt.addAll(cl);
		return rt;

	}

	public static <T> T single(Collection<T> cl, boolean force) {
		return single(cl, force, null);
	}

	public static <T> T single(Collection<T> cl, boolean force, Object msg) {
		String amsg = msg == null ? "" : "," + msg.toString();
		if (cl.isEmpty()) {
			if (force) {
				throw new FsException("force:" + cl + amsg);
			} else {
				return null;
			}
		} else if (cl.size() > 1) {
			throw new FsException("tomuch:" + cl + amsg);
		} else {
			return cl.iterator().next();
		}
	}
}
