/**
 *  Jan 16, 2013
 */
package com.graphscape.gwt.test.core.cases;

import com.google.gwt.junit.client.GWTTestCase;
import com.graphscape.gwt.core.html5.DefaultJSO;

/**
 * @author wuzhen
 * 
 */
public class JsoTest extends GWTTestCase {
	/* */
	@Override
	public String getModuleName() {

		return "com.fs.commons.uicore.impl.test.gwt.UiCoreImplTest";

	}

	public void test() {
		DefaultJSO jso = DefaultJSO.newInstance();
		jso.setProperty("b1", true);
		jso.setProperty("d1", 1.1D);

		Object o = jso.getProperty("b1");
		assertNotNull("b1 should be true", o);
		assertTrue(o instanceof Boolean);
		assertTrue((Boolean) o);

		String s = jso.toLongString();
		System.out.println(s);
	}
}
