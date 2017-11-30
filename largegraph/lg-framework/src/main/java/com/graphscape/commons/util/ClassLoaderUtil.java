/**
 *  
 */
package com.graphscape.commons.util;

import java.io.InputStream;

import com.graphscape.commons.lang.GsException;

/**
 * @author wu
 * 
 */
public class ClassLoaderUtil {
	public static InputStream getResourceAsStream(String resource) {
		return getResourceAsStream(resource, false);
	}

	public static InputStream getResourceAsStream(String resource, boolean force) {

		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		InputStream rt = cl.getResourceAsStream(resource);
		if (rt == null && force) {
			throw new GsException("no resource found:" + resource);
		}
		return rt;

	}
}
