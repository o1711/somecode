/**
 * Jun 30, 2012
 */
package com.graphscape.gwt.commons.provider.default_.editor.basic;

import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.DOM;
import com.graphscape.gwt.commons.editor.basic.IntegerEditorI;
import com.graphscape.gwt.commons.editor.support.EditorSupport;
import com.graphscape.gwt.commons.provider.default_.editor.basic.IntegerEditorImpl;
import com.graphscape.gwt.core.ContainerI;

/**
 * @author wu
 * 
 */
public class IntegerEditorImpl extends EditorSupport<Integer> implements IntegerEditorI {

	/** */
	public IntegerEditorImpl(ContainerI c, String name) {
		super(c, name, DOM.createInputText());

		this.addGwtHandler(com.google.gwt.event.dom.client.ChangeEvent.getType(), new ChangeHandler() {

			@Override
			public void onChange(com.google.gwt.event.dom.client.ChangeEvent event) {
				IntegerEditorImpl.this.onChange();
			}
		});
	}

	protected void onChange() {
		String s = this.getText();
		Integer v = null;
		try {
			v = Integer.parseInt(s);
		} catch (NumberFormatException e) {
			// TODO error indicator
		}

		this.setData(Integer.valueOf(v), true);//

	}

	/* */
	@Override
	protected boolean setData(Integer dt, boolean dis) {
		boolean rt = super.setData(dt, dis);
		if(!rt){
			return false;
		}
		String txt = dt == null ? "" : dt + "";
		this.setText(txt);
		return true;
	}

	public String getText() {
		return DOM.getElementProperty(getElement(), "value");
	}

	protected void setText(String txt) {
		DOM.setElementProperty(getElement(), "value", txt);
	}

}
