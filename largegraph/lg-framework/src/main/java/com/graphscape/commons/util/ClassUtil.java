/**
 * 
 */
package com.graphscape.commons.util;

import com.graphscape.commons.lang.GsException;

/**
 * As the entry point for class operation.
 * 
 * @author wuzhen
 * 
 */
public class ClassUtil {

	public static <T> T newInstance(Class<T> cls) {
		return newInstance(cls, new Class<?>[] {}, new Object[] {});
	}

	public static <T> T newInstance(String cName) {
		return newInstance(cName, new Class<?>[] {}, new Object[] {});
	}

	public static <T> T newInstance(String cName, Class<?>[] paramTypes,
			Object[] paramValues) {

		Class<T> c = forName(cName);
		return newInstance(c, paramTypes, paramValues);
	}

	public static <T> T newInstance(Class<T> cls, Class<?>[] paramTypes,
			Object[] paramValues) {
		T rt;
		try {
			rt = cls.getConstructor(paramTypes).newInstance(paramValues);

		} catch (Exception e) {
			throw new GsException("[cannot instance:class:" + cls
					+ ",constructor not exist?]", e);
		}
		return rt;
	}

	public static <T> Class<T> forName(String cName) {
		return forName(cName, true);
	}

	public static <T> Class<T> forName(String cName, boolean force) {

		try {
			return (Class<T>) Class.forName(cName);
		} catch (ClassNotFoundException e) {
			if (force) {
				throw GsException.toRuntimeException(e);
			}
			return null;

		}

	}
}
