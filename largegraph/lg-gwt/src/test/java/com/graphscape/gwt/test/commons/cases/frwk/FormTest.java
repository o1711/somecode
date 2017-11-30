/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 6, 2012
 */
package com.graphscape.gwt.test.commons.cases.frwk;

import com.graphscape.gwt.commons.frwk.commons.FieldModel;
import com.graphscape.gwt.commons.provider.default_.frwk.commons.form.FormView;
import com.graphscape.gwt.commons.widget.EditorI;
import com.graphscape.gwt.commons.widget.event.ChangeEvent;
import com.graphscape.gwt.core.core.Event.EventHandlerI;
import com.graphscape.gwt.core.data.property.ObjectPropertiesData;
import com.graphscape.gwt.test.commons.cases.support.TestBase;

/**
 * @author wu
 * 
 */
public class FormTest extends TestBase {

	private String field1Data = ("field1-data");

	public void testFormView() {
		this.finishing.add("field1");

		System.out.println(this.client.dump());
		System.out.println(this.root.dump());

		final FormView fv = new FormView(this.container, "testform");
		// TODO add to body
		// assertTrue("form view should attached.", fv.isAttached());

		this.delayTestFinish(timeoutMillis);
		FieldModel f1m = fv.addField("field1", String.class);
		//
		EditorI fE = fv.getEditor("field1");
		fE.addHandler(ChangeEvent.TYPE, new EventHandlerI<ChangeEvent>(){
			
			@Override
			public void handle(ChangeEvent t) {
				FormTest.this.onFieldData(fv);
				
			}});
		fE.input(this.field1Data);

		this.delayTestFinish(this.timeoutMillis);
		
	}
	public void onFieldData(FormView fv){
		ObjectPropertiesData d = fv.getData();
		assertNotNull("form data is null", d);
		String sd = (String) d.getProperty("field1");
		assertNotNull("field1 is null", sd);
		assertEquals("", this.field1Data, sd);

		this.tryFinish("field1");
	}

}
