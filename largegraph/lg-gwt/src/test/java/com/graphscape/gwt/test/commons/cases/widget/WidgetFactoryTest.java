/**
 * Jun 13, 2012
 */
package com.graphscape.gwt.test.commons.cases.widget;

import com.graphscape.gwt.commons.editor.basic.IntegerEditorI;
import com.graphscape.gwt.commons.editor.basic.StringEditorI;
import com.graphscape.gwt.commons.widget.basic.ButtonI;
import com.graphscape.gwt.commons.widget.basic.LabelI;
import com.graphscape.gwt.commons.widget.event.ChangeEvent;
import com.graphscape.gwt.commons.widget.list.ListI;
import com.graphscape.gwt.core.commons.Holder;
import com.graphscape.gwt.core.core.Event.EventHandlerI;
import com.graphscape.gwt.core.event.ClickEvent;
import com.graphscape.gwt.test.commons.cases.support.TestBase;

/**
 * @author wuzhen
 * 
 */
public class WidgetFactoryTest extends TestBase {

	public void testList() {

		ListI ls = wf.create(ListI.class);
		ls.parent(this.root);//

		ButtonI b = wf.create(ButtonI.class);

		ls.addChild(b);

		LabelI lb = wf.create(LabelI.class);
		ls.addChild(lb);

	}

	public void testStringEditor() {
		StringEditorI se = wf.create(StringEditorI.class);
		final String sd = "this is string value";
		se.addHandler(ChangeEvent.TYPE, new EventHandlerI<ChangeEvent>() {

			@Override
			public void handle(ChangeEvent e) {
				WidgetFactoryTest.this.onChangeEvent_testStringEditor(sd, e);
			}
		});
		se.input(sd);

		this.delayTestFinish(timeoutMillis);
	}

	private void onChangeEvent_testStringEditor(String expected, ChangeEvent e) {
		String string = (String) e.getData();
		assertEquals(expected, string);
		this.finishTest();
	}

	public void testIntegerEditor() {
		IntegerEditorI se = wf.create(IntegerEditorI.class);
		final Integer expected = 10;
		se.addHandler(ChangeEvent.TYPE, new EventHandlerI<ChangeEvent>() {

			@Override
			public void handle(ChangeEvent e) {
				WidgetFactoryTest.this.onChangeEvent_testIntegerEditor(expected, e);
			}
		});
		se.input(expected);

		this.delayTestFinish(timeoutMillis);
	}

	private void onChangeEvent_testIntegerEditor(Integer exp, ChangeEvent e) {
		Integer act = (Integer) e.getData();
		assertEquals(exp, act);
		this.finishTest();
	}

	public void testButtonClick() {
		//
		ButtonI b = wf.create(ButtonI.class);
		b.addHandler(ClickEvent.TYPE, new EventHandlerI<ClickEvent>() {

			@Override
			public void handle(ClickEvent e) {
				WidgetFactoryTest.this.onClickEvent_testButtonClick(e);
			}
		});
		b.parent(this.root);// NOTE
		b.getElementWrapper().click();
		this.delayTestFinish(timeoutMillis);

	}

	private void onClickEvent_testButtonClick(ClickEvent e) {
		this.finishTest();

	}
}
