/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 4, 2012
 */
package com.graphscape.gwt.test.commons.cases.widget;

import com.graphscape.gwt.commons.widget.tab.TabWI;
import com.graphscape.gwt.commons.widget.tab.TabberWI;
import com.graphscape.gwt.core.commons.Path;
import com.graphscape.gwt.test.commons.cases.support.TestBase;

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
