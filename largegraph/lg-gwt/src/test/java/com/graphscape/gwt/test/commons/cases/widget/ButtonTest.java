/**
 * Jun 13, 2012
 */
package com.graphscape.gwt.test.commons.cases.widget;

import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.user.client.Element;
import com.graphscape.gwt.commons.widget.basic.ButtonI;
import com.graphscape.gwt.core.core.Event.EventHandlerI;
import com.graphscape.gwt.core.event.ClickEvent;
import com.graphscape.gwt.test.commons.cases.support.TestBase;

/**
 * @author wuzhen
 * 
 */
public class ButtonTest extends TestBase {

	
	public void testButtonClickEvent() {
		//
		ButtonI b = wf.create(ButtonI.class);
		b.addHandler(ClickEvent.TYPE, new EventHandlerI<ClickEvent>() {

			@Override
			public void handle(ClickEvent e) {
				System.out.println("click event listened.");
				ButtonTest.this.finishTest();
			}
		});
		b.parent(this.root);// NOTE
		this.delayTestFinish(this.timeoutMillis);
		b.getElementWrapper().click();//

	}

	public void dump(int depth, Element ele) {
		String s = "";
		for (int i = 0; i < depth; i++) {
			s += " ";
		}
		log(s + "<" + ele.getTagName() + "/>");
		NodeList<Node> nl = ele.getChildNodes();
		for (int i = 0; i < nl.getLength(); i++) {
			Element eI = (Element) nl.getItem(i);
			dump(depth + 1, eI);
		}
	}

	public void log(String msg) {
		System.out.println(msg);
	}
}
