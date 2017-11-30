/**
 * Jun 30, 2012
 */
package com.graphscape.gwt.commons.provider.default_.editor.basic;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.graphscape.gwt.commons.editor.basic.DateEditorI;
import com.graphscape.gwt.commons.editor.support.EditorSupport;
import com.graphscape.gwt.commons.provider.default_.editor.basic.DateEditorImpl;
import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.commons.UiPropertiesI;
import com.graphscape.gwt.core.data.basic.DateData;
import com.graphscape.gwt.core.gwthandlers.GwtChangeHandler;
import com.graphscape.gwt.core.support.ObjectElementHelper;
import com.graphscape.gwt.core.util.DateUtil;

/**
 * @author wu
 * 
 */
public class DateEditorImpl extends EditorSupport<DateData> implements DateEditorI {

	private static String FS = "MM/dd/yyyy";

	private static DateTimeFormat FORMAT = DateTimeFormat.getFormat(FS);

	private Element input;
	
	private Element label;

	/** */
	public DateEditorImpl(ContainerI c, String name, UiPropertiesI pts) {
		super(c, name, DOM.createDiv(), pts);
		this.element.addClassName("date-editor");
		// this.stringElement = DOM.createElement("INPUT");
		// this.stringElement.setAttribute("type", "date");//HTML5
		this.input = DOM.createInputText();
		this.input.addClassName("date");
		this.element.appendChild(this.input);
		this.label = DOM.createDiv();
		this.element.appendChild(this.label);
		this.label.addClassName("explain");
		this.label.setInnerText("("+FS+")");
		
		//this.input.setAttribute("value", FS);//init value
		

		ObjectElementHelper oeh = this.helpers.addHelper("stringElement", input);

		oeh.addGwtHandler(com.google.gwt.event.dom.client.ChangeEvent.getType(), new GwtChangeHandler() {

			@Override
			public void handleInternal(com.google.gwt.event.dom.client.ChangeEvent event) {
				DateEditorImpl.this.onChange();
			}
		});

	}

	protected void onChange() {

		String dS = this.getText();
		Date dd = null;
		if (dS != null) {
			try {
				dd = FORMAT.parse(dS);
			} catch (IllegalArgumentException e) {
				//ignore,null value 
			}
		}
		
		DateData ddd = DateData.valueOf(dd);
		this.setData(ddd, true);//

	}

	/* */
	@Override
	public boolean setData(DateData dt, boolean dis) {
		boolean changed = super.setData(dt, dis);
		if (!changed) {
			return false;
		}
		Date date = new Date(dt.getValue());
		
		String txt = dt == null ? "" : FORMAT.format(date);
		this.setText(txt);
		return changed;

	}

	public String getText() {
		String rt = DOM.getElementProperty(this.input, "value");
		rt = rt == null ? null : rt.trim();
		return rt;

	}

	protected void setText(String txt) {
		DOM.setElementProperty(this.input, "value", txt);
	}

}
