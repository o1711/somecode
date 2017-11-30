package org.ta.common.util;

public class TaObjectUtil {

	public static boolean nullSafeEquals(Object o1, Object o2){
		
		if(o1 == null){
			return o2 == null;
		}
		return o1.equals(o2);
	}
}
