/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 10, 2012
 */
package com.graphscape.gwt.test.core;

import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.WidgetFactoryI;
import com.graphscape.gwt.core.commons.UiPropertiesI;
import com.graphscape.gwt.core.reflect.InstanceOf;
import com.graphscape.gwt.core.reflect.InstanceOf.CheckerSupport;
import com.graphscape.gwt.core.spi.GwtSPI;
import com.graphscape.gwt.core.support.WidgetCreaterSupport;
import com.graphscape.gwt.test.core.widget.TestWI;
import com.graphscape.gwt.test.core.widget.TestWidget;

/**
 * @author wu
 * 
 */
public class UiCoreTestGPI implements GwtSPI {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.commons.uicore.api.gwt.client.spi.GwtSPI#active(com.fs.commons.uicore.
	 * client.ContainerI)
	 */
	@Override
	public void active(ContainerI c) {

		InstanceOf.addChecker(new CheckerSupport(TestWI.class) {

			@Override
			public boolean isInstance(Object o) {
				return o instanceof TestWI;
			}
		});

		c.get(WidgetFactoryI.class, true).addCreater(new WidgetCreaterSupport<TestWI>(TestWI.class) {

			@Override
			public TestWI create(ContainerI c, String name,UiPropertiesI<Object> pts) {
				// TODO Auto-generated method stub
				return new TestWidget(c, name);
			}
		});
	}

}
