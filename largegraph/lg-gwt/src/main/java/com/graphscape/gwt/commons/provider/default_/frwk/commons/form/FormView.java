/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 12, 2012
 */
package com.graphscape.gwt.commons.provider.default_.frwk.commons.form;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.DOM;
import com.graphscape.gwt.commons.editor.basic.BooleanEditorI;
import com.graphscape.gwt.commons.editor.basic.DateEditorI;
import com.graphscape.gwt.commons.editor.basic.IntegerEditorI;
import com.graphscape.gwt.commons.editor.basic.StringEditorI;
import com.graphscape.gwt.commons.editor.properties.PropertiesEditorI;
import com.graphscape.gwt.commons.editor.properties.PropertiesEditorI.PropertyModel;
import com.graphscape.gwt.commons.frwk.commons.FieldModel;
import com.graphscape.gwt.commons.frwk.commons.FormModel;
import com.graphscape.gwt.commons.frwk.commons.FormViewI;
import com.graphscape.gwt.commons.mvc.support.ViewSupport;
import com.graphscape.gwt.commons.widget.EditorI;
import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.UiException;
import com.graphscape.gwt.core.commons.Path;
import com.graphscape.gwt.core.core.UiCallbackI;
import com.graphscape.gwt.core.data.basic.DateData;
import com.graphscape.gwt.core.data.property.ObjectPropertiesData;

/**
 * @author wu
 * 
 */
public class FormView extends ViewSupport implements FormViewI {

	private static Map<Class, Class<? extends EditorI>> typeEditorMap = new HashMap<Class, Class<? extends EditorI>>();

	private Map<String, Class<? extends EditorI>> fieldEditorMap = new HashMap<String, Class<? extends EditorI>>();

	private PropertiesEditorI propertiesEditor;

	private FormModel form;

	static {
		typeEditorMap.put(String.class, StringEditorI.class);
		typeEditorMap.put(Boolean.class, BooleanEditorI.class);
		typeEditorMap.put(Integer.class, IntegerEditorI.class);
		typeEditorMap.put(DateData.class, DateEditorI.class);
		

	}

	/**
	 * @param ctn
	 */
	public FormView(ContainerI c, String name) {
		super(c, name, DOM.createDiv());
		this.form = new FormModel(name);
		this.propertiesEditor = this.factory.create(PropertiesEditorI.class);
		this.propertiesEditor.parent(this);//

	}
	
	public PropertiesEditorI getEditor(){
		return this.propertiesEditor;
	}

	public EditorI getEditor(String name) {
		return this.propertiesEditor.getPropertyEditor(name);
	}

	protected Class<? extends EditorI> resolveEditorClass(FieldModel fm) {

		Class<? extends EditorI> rt = fm.getEditorClass();

		if (rt != null) {
			return rt;
		}

		String fname = fm.getName();
		rt = this.fieldEditorMap.get(fname);
		if (rt != null) {
			return rt;
		}
		Class<?> fType = fm.getFieldType();
		rt = typeEditorMap.get(fType);
		if (rt == null) {
			throw new UiException("no editor is configured for field:" + fname + ",type:" + fType);
		}
		return rt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.commons.uicommons.api.gwt.client.frwk.commons.FormViewI#getFormModel()
	 */
	@Override
	public FormModel getFormModel() {
		// TODO Auto-generated method stub
		return this.form;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.commons.uicommons.api.gwt.client.frwk.commons.FormViewI#getData()
	 */
	@Override
	public ObjectPropertiesData getData() {

		return this.propertiesEditor.getData();

	}

	@Override
	public <T extends EditorI> FieldModel addField(String name, Class<?> dcls) {
		return this.addField(name, dcls, null, null, null);// default editor
															// class
	}

	@Override
	public <T extends EditorI> FieldModel addField(String name, Class<?> dcls, Map<String, Object> editorPts) {
		return this.addField(name, dcls, null, editorPts, null);
	}

	@Override
	public <T extends EditorI> FieldModel addField(String name, Class<?> dcls, Class<T> editorClass) {
		return this.addField(name, dcls, editorClass, null, null);
	}

	@Override
	public <T extends EditorI> FieldModel addField(String name, Class<?> dcls, Class<T> editorClass,
			final UiCallbackI<T, Object> editorCallback) {
		return this.addField(name, dcls, editorClass, null, editorCallback);
	}

	@Override
	public <T extends EditorI> FieldModel addField(String name, Class<?> dcls, Class<T> editorClass,
			Map<String, Object> editorPts) {
		return this.addField(name, dcls, editorClass, editorPts, null);
	}

	public <T extends EditorI> FieldModel addField(String name, Class<?> dcls, Class<T> editorClass,
			Map<String, Object> editorPts, final UiCallbackI<T, Object> editorCallback) {

		FieldModel rt = new FieldModel(name, dcls, editorClass);

		this.form.addField(rt);

		Class<? extends EditorI> etype = this.resolveEditorClass(rt);//
		String i18n = rt.getName();
		PropertyModel pm = this.propertiesEditor.addFieldModel(rt.getName(), etype, editorPts, i18n);
		if (editorCallback != null) {

			editorCallback.execute((T) pm.getEditor(true));

		}
		EditorI editor = pm.getEditor(true);
		rt.setEditor(editor);
		return rt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.commons.uicommons.api.gwt.client.frwk.commons.FormViewI#getActionList()
	 */
	@Override
	public List<Path> getActionList() {

		return this.form.getActionList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.commons.uicommons.api.gwt.client.frwk.commons.FormViewI#getFieldData(java
	 * .lang.String, java.lang.Object)
	 */
	@Override
	public <T> T getFieldData(String fname, T def) {
		ObjectPropertiesData pts = this.getData();

		return (T) pts.getProperty(fname, def);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.commons.uicommons.api.gwt.client.frwk.commons.FormViewI#getFieldData(java
	 * .lang.String)
	 */
	@Override
	public <T> T getFieldData(String fname) {
		// TODO Auto-generated method stub
		return this.getFieldData(fname, null);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.commons.uicommons.api.gwt.client.frwk.commons.FormViewI#setFieldValue(
	 * java.lang.String, java.lang.Object)
	 */
	@Override
	public void setFieldValue(String fname, Object value) {
		FieldModel fm = this.getFormModel().getFieldModel(fname, false);
		if (fm == null) {
			throw new UiException("no field with name:" + fname + " in view:" + this);
		}
		this.propertiesEditor.setFieldValue(fname, value);
	}

}
