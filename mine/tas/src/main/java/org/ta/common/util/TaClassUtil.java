/**
 * Nov 4, 2011
 */
package org.ta.common.util;

import org.ta.common.config.TaException;



/**
 * @author wuzhen
 * 
 */
public class TaClassUtil {

	public static <T> T newInstance(Class<T> cls) {
		return newInstance(cls, new Class<?>[] {}, new Object[] {});
	}

	public static <T> T newInstance(String cName) {
		return (T)newInstance(cName, new Class<?>[] {}, new Object[] {});
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
			throw new TaException("cannot instance : class:" + cls + "]", e);
		}
		return rt;
	}

	public static <T> Class<T> forName(String cName) {

		try {
			return (Class<T>) Class.forName(cName);
		} catch (ClassNotFoundException e) {
			throw TaException.toRuntimeException(e);
		}

	}
}
