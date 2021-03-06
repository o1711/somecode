/**
 * Jun 25, 2012
 */
package org.cellang.clwt.core.client.lang;

import java.util.HashMap;
import java.util.Map;

import org.cellang.clwt.core.client.UiException;

/**
 * @author wuzhen
 * 
 */
public class InstanceOf {

	public static interface CheckerI {

		public Class getCheckClass();

		public boolean isInstance(Object o);

	}

	public static abstract class CheckerSupport implements InstanceOf.CheckerI {

		protected Class checkClass;

		public CheckerSupport(Class cc) {
			this.checkClass = cc;
		}

		/*
		
		 */
		@Override
		public Class getCheckClass() {
			return this.checkClass;
		}

	}

	private static Map<Class, CheckerI> map = new HashMap<Class, CheckerI>();

	public static boolean addChecker(CheckerI ck) {
		map.put(ck.getCheckClass(), ck);
		return true;
	}

	public static boolean isInstance(Class cls, Object o) {
		CheckerI ck = map.get(cls);
		if (ck == null) {
			throw new UiException("no instanceof checker found:" + cls
					+ ",either a plugin not actived or not added it in the your plugin.");
		}
		return ck.isInstance(o);
	}

}
