/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 4, 2012
 */
package com.graphscape.gwt.test.commons.cases.widget;

import com.graphscape.gwt.commons.widget.wpanel.WindowPanelWI;
import com.graphscape.gwt.core.WindowI;
import com.graphscape.gwt.core.commons.Size;
import com.graphscape.gwt.test.commons.cases.support.TestBase;

/**
 * @author wu
 * 
 */
public class WindowPanelTest extends TestBase {

	WindowPanelWI tw;
	Size size;

	public void testWindowPanel() {
		tw = wf.create(WindowPanelWI.class);

		tw.parent(this.root);// TODO assert attached.

		assertTrue("tw not attached", tw.isAttached());

		WindowI w = this.container.get(WindowI.class, true);

	}

}
