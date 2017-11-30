/**
 * Jul 1, 2012
 */
package com.graphscape.gwt.test.commons.cases.widget;

import com.graphscape.gwt.commons.widget.basic.LabelI;
import com.graphscape.gwt.commons.widget.table.TableI;
import com.graphscape.gwt.commons.widget.table.TableI.CellI;
import com.graphscape.gwt.commons.widget.table.TableI.HeaderI;
import com.graphscape.gwt.commons.widget.table.TableI.RowI;
import com.graphscape.gwt.core.commons.Holder;
import com.graphscape.gwt.test.commons.cases.support.TestBase;

/**
 * @author wu
 * 
 */
public class TableTest extends TestBase {

	public void testTable() {
		//
		TableI t = wf.create(TableI.class);
		// headers
		String[] names = new String[] { "header1,header2,header3" };
		String[][] values = new String[][] { //
		new String[] { "v01", "v02", "v03" },//
				new String[] { "v11", "v12", "v13" },//
				new String[] { "v21", "v22", "v23" },//
		//
		};
		for (int i = 0; i < names.length; i++) {
			String name = names[i];
			LabelI hw = wf.create(LabelI.class);
			HeaderI h = t.getHeaders().createHeader(name);
			h.child(hw);//
		}
		//
		for (String[] r : values) {
			RowI rw = t.getBody().createRow();
			for (String c : r) {
				CellI ci = rw.createCell();
				LabelI li = wf.create(LabelI.class);// TODO values
				ci.child(li);
			}
		}
		final Holder<Boolean> clicked = new Holder<Boolean>();
		t.parent(this.root);// NOTE

		// assertTrue("button click not notified.", clicked.getTarget());

	}
}
