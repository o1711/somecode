/**
 * Jul 1, 2012
 */
package com.graphscape.gwt.test.commons.cases.widget;

import com.graphscape.gwt.commons.editor.basic.BooleanEditorI;
import com.graphscape.gwt.commons.editor.basic.IntegerEditorI;
import com.graphscape.gwt.commons.editor.basic.StringEditorI;
import com.graphscape.gwt.commons.editor.properties.PropertiesEditorI;
import com.graphscape.gwt.commons.widget.event.ChangeEvent;
import com.graphscape.gwt.core.core.Event.EventHandlerI;
import com.graphscape.gwt.core.data.property.ObjectPropertiesData;
import com.graphscape.gwt.test.commons.cases.support.TestBase;

/**
 * @author wu
 * 
 */
public class PropertiesEditorTest extends TestBase {

	public PropertiesEditorTest() {
		this.start = false;
	}

	public void testPropertiesEditor() {

		this.finishing.add("changed");

		ObjectPropertiesData dt = new ObjectPropertiesData();
		dt.setProperty("integer1", null);
		dt.setProperty("string1", null);
		dt.setProperty("string2", null);
		dt.setProperty("boolean1", null);//
		//

		PropertiesEditorI edt = wf.create(PropertiesEditorI.class);//
		edt.addFieldModel("integer1", IntegerEditorI.class);
		edt.addFieldModel("string1", StringEditorI.class);
		edt.addFieldModel("string2", StringEditorI.class);
		edt.addFieldModel("boolean1", BooleanEditorI.class);
		edt.parent(this.root);// attach

		assertTrue("should be attached.", edt.isAttached());

		final Integer expect = Integer.valueOf(1);
		edt.addHandler(ChangeEvent.TYPE, new EventHandlerI<ChangeEvent>() {

			@Override
			public void handle(ChangeEvent e) {
				PropertiesEditorTest.this.onChange(expect, e);

			}
		});

		this.delayTestFinish(this.timeoutMillis);
		IntegerEditorI ie = edt.find(IntegerEditorI.class, false);
		ie.input(expect);//
	}

	public void onChange(Integer expect, ChangeEvent e) {

		// integer property

		ObjectPropertiesData data = e.getData();
		Object actual = data.getProperty("integer1", false);
		assertNotNull(actual);
		assertEquals(expect, actual);
		this.tryFinish("changed");
	}
}
