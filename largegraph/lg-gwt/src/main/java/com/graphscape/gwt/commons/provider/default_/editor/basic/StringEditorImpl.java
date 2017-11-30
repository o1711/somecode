/**
 * Jun 30, 2012
 */
package com.graphscape.gwt.commons.provider.default_.editor.basic;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.graphscape.gwt.commons.Constants;
import com.graphscape.gwt.commons.editor.basic.StringEditorI;
import com.graphscape.gwt.commons.editor.support.EditorSupport;
import com.graphscape.gwt.commons.provider.default_.editor.basic.StringEditorImpl;
import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.UiException;
import com.graphscape.gwt.core.commons.UiPropertiesI;
import com.graphscape.gwt.core.event.KeyDownEvent;
import com.graphscape.gwt.core.event.KeyUpEvent;
import com.graphscape.gwt.core.gwthandlers.GwtChangeHandler;
import com.graphscape.gwt.core.gwthandlers.GwtKeyDownHandler;
import com.graphscape.gwt.core.gwthandlers.GwtKeyUpHandler;
import com.graphscape.gwt.core.support.ObjectElementHelper;

/**
 * @author wu
 * 
 */
public class StringEditorImpl extends EditorSupport<String> implements StringEditorI {

	private boolean trim = true;
	private boolean emptyAsNull = true;
	private boolean isTextArea;

	private int lengthLimit = -1;

	private Element stringElement;

	/** */
	public StringEditorImpl(ContainerI c, String name, UiPropertiesI pts) {
		super(c, name, DOM.createDiv(), pts);
		this.isTextArea = (Boolean) this.getProperty(StringEditorI.PK_TEXAREA, Boolean.FALSE);
		if (this.isTextArea) {
			stringElement = DOM.createTextArea();
		} else {
			boolean isPassword = (Boolean) this.getProperty(StringEditorI.PK_ISPASSWORD, Boolean.FALSE);
			if (isPassword) {
				stringElement = DOM.createInputPassword();
			} else {
				stringElement = DOM.createInputText();
			}
		}

		int ll = (Integer) this.getProperty(StringEditorI.PK_LENGTH_LIMIT, -1);
		// not set,see the default config in client parameters
		if (ll == -1) {
			ll = this.getClient(true).getParameterAsInt(Constants.CPK_TEXT_INPUT_LENGTH_LIMIT, -1);
		}

		if (ll < -1) {
			throw new UiException("" + StringEditorI.PK_LENGTH_LIMIT + ",must >= -1");
		}

		this.lengthLimit = ll;

		this.element.appendChild(this.stringElement);
		ObjectElementHelper oeh = this.helpers.addHelper("stringElement", stringElement);
		oeh.addGwtHandler(com.google.gwt.event.dom.client.ChangeEvent.getType(), new GwtChangeHandler() {

			@Override
			public void handleInternal(com.google.gwt.event.dom.client.ChangeEvent event) {
				StringEditorImpl.this.onChange();
			}
		});
		oeh.addGwtHandler(com.google.gwt.event.dom.client.KeyDownEvent.getType(), new GwtKeyDownHandler() {

			@Override
			protected void handleInternal(com.google.gwt.event.dom.client.KeyDownEvent evt) {
				StringEditorImpl.this.onKeyDown(evt);
			}
		});
		oeh.addGwtHandler(com.google.gwt.event.dom.client.KeyUpEvent.getType(), new GwtKeyUpHandler() {

			@Override
			protected void handleInternal(com.google.gwt.event.dom.client.KeyUpEvent evt) {
				StringEditorImpl.this.onKeyUp(evt);
			}
		});
	}

	protected void onKeyUp(com.google.gwt.event.dom.client.KeyUpEvent evt) {
		this.onChange();
		new KeyUpEvent(this, evt.getNativeKeyCode(), evt.isControlKeyDown()).dispatch();
	}

	/**
	 * Apr 2, 2013
	 */
	protected void onKeyDown(com.google.gwt.event.dom.client.KeyDownEvent evt) {
		new KeyDownEvent(this, evt.getNativeKeyCode(), evt.isControlKeyDown()).dispatch();
	}

	protected void onChange() {

		String v = this.getText();

		if (this.lengthLimit != -1 && v != null && v.length() > this.lengthLimit) {
			// not show a message for this?
			v = v.substring(0, this.lengthLimit);
		}

		this.setData((v), true);//

	}

	/* */
	@Override
	public boolean setData(String dt, boolean dis) {
		boolean changed = super.setData(dt, dis);
		if (!changed) {
			return false;
		}
		String txt = dt == null ? "" : dt;
		this.setText(txt);
		return changed;

	}

	public String getText() {
		String rt = DOM.getElementProperty(this.stringElement, "value");

		if (this.trim) {
			if (rt != null) {
				rt = rt.trim();
			}
		}

		if (this.emptyAsNull && "".equals(rt)) {
			rt = null;
		}

		return rt;

	}

	protected void setText(String txt) {
		DOM.setElementProperty(this.stringElement, "value", txt);
	}

}
