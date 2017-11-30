/**
 * Jul 16, 2012
 */
package com.graphscape.gwt.test.core.cases;

import com.graphscape.gwt.core.WidgetFactoryI;
import com.graphscape.gwt.test.core.cases.support.TestBase;
import com.graphscape.gwt.test.core.widget.TestWI;

/**
 * @author wu
 * 
 */
public class WidgetFactoryTest extends TestBase {
	
	public WidgetFactoryTest(){
		super(false);
	}
	
	public void testWidgetFactory() {

		WidgetFactoryI wf = this.factory.getContainer().get(
				WidgetFactoryI.class, true);
		TestWI tw = wf.create(TestWI.class);
		
	}
}
