/**
 *  Nov 26, 2012
 */
package com.graphscape.commons.util;

/**
 * @author wuzhen
 * 
 */
public class ObjectUtil {

	public static boolean nullSafeEquals(Object o1, Object o2) {
		if (o1 == null) {
			return (o2 == null);
		}
		return o1.equals(o2);
	}
}
