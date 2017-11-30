/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 4, 2012
 */
package com.fs.uicommons.impl.test.gwt.client.cases.widget;

import com.fs.uicommons.api.gwt.client.widget.panel.PanelWI;
import com.fs.uicommons.api.gwt.client.widget.tab.TabWI;
import com.fs.uicommons.api.gwt.client.widget.tab.TabberWI;
import com.fs.uicommons.impl.test.gwt.client.cases.support.TestBase;
import com.fs.uicore.api.gwt.client.commons.Path;

/**
 * @author wu
 * 
 */
public class TabberTest extends TestBase {

	public TabberTest(){
		this.start = false;
	}
	
	public void testTabber() {
		Path path1 = Path.valueOf("tab1");
		Path path2 = Path.valueOf("tab2");
		
		TabberWI tw = wf.create(TabberWI.class);
		tw.parent(this.root);// TODO assert attached.
		
		TabWI tab1 = tw.addTab(path1);
		TabWI tab2 = tw.addTab(path2);

		assertTrue("tab1 should selected", tab1.isSelected());
		assertFalse("tab2 should not be selcted", tab2.isSelected());

		tab2._click();// tab2 will be selected.
		
		assertTrue("tab2 should be selected", tab2.isSelected());
		
		tab1 = tw.getTab(path1, false);
		assertNotNull("get by path failure",tab1);
		tw.remove(path1);
		
		tab1 = tw.getTab(path1, false);
		assertNull("should be removed",tab1);
		
	}

}
