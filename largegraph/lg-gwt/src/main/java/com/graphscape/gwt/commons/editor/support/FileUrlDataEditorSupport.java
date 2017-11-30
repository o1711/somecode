/**
 * All right is from Author of the file.
 * Any usage of the code must be authorized by the the auther.
 * If not sure the detail of the license,please distroy the copies immediately.  
 * Nov 16, 2012
 */
package com.graphscape.gwt.commons.editor.support;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsDate;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.user.client.DOM;
import com.graphscape.gwt.commons.editor.support.EditorSupport;
import com.graphscape.gwt.commons.editor.support.FileUrlDataEditorSupport;
import com.graphscape.gwt.commons.html5.file.FileReaderJSO;
import com.graphscape.gwt.commons.html5.file.FileRefJSO;
import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.HandlerI;
import com.graphscape.gwt.core.UiException;
import com.graphscape.gwt.core.commons.UiPropertiesI;
import com.graphscape.gwt.core.dom.ElementWrapper;
import com.graphscape.gwt.core.gwthandlers.GwtChangeHandler;
import com.graphscape.gwt.core.support.ObjectElementHelper;

/**
 * @author wuzhen
 *         <p>
 *         This implementation require html5.
 */
public class FileUrlDataEditorSupport extends EditorSupport<String> {

	private int maxSize = 10 * 1024 * 1024;// max size of file processing.

	protected ObjectElementHelper input;

	protected ElementWrapper dataRender;
	
	protected boolean functional;

	/**
	 * @param ele
	 */
	public FileUrlDataEditorSupport(ContainerI c, String name, UiPropertiesI<Object> pts) {
		super(c, name, DOM.createDiv(),pts);
		this.dataRender = new ElementWrapper(DOM.createDiv());
		this.elementWrapper.append(dataRender);//
		

		//
		// input = DOM.createElement("input");
		// this.input.setAttribute("type", "file");
		// DOM.appendChild(this.element, this.input);//

		// ?multiple
		// TODO add outer element.
		this.functional = FileReaderJSO.isSupport();
		
		if (this.functional) {
			this.input = this.helpers.addHelper("input", DOM.createElement("input"));
			this.getMasterHelper().append(this.input);//
			this.input.setAttribute("type", "file");
			this.input.addGwtHandler(com.google.gwt.event.dom.client.ChangeEvent.getType(),
					new GwtChangeHandler() {

						@Override
						protected void handleInternal(ChangeEvent evt) {
							FileUrlDataEditorSupport.this.onChange(evt);
							//

						}
					});
		}else{
			this.dataRender.getElement().setInnerText("Your browser not support html5 file feader!");
		}
	}

	/**
	 * see:http://www.html5rocks.com/en/tutorials/file/dndfiles/
	 */
	protected void onChange(com.google.gwt.event.dom.client.ChangeEvent evt) {

		JsArray<FileRefJSO> flist = FileRefJSO.getFileList(this.input.getElement());//

		if (flist.length() > 1) {
			throw new UiException("multiple files is not supported");
		}
		if (flist.length() == 0) {
			this.setData(null, true);//
			return;// TODO null

		}
		FileRefJSO file = flist.get(0);
		if (file.getSize() > this.maxSize) {
			throw new UiException("not supported size:" + file.getSize() + ",max size:" + this.maxSize);
		}
		String name = file.getName();//
		JsDate jsd = file.getLastModifiedDate();

		long last = jsd == null ? 0L : (long) jsd.getMilliseconds();// TODO
																	// int
																	// to
																	// long?
		final FileReaderJSO fr = FileReaderJSO.newInstance();

		fr.onLoadEnd(new HandlerI<JavaScriptObject>() {

			@Override
			public void handle(JavaScriptObject t) {
				FileUrlDataEditorSupport.this.onLoadEnd(fr, t);
			}
		}).readAsDataURL(file);
	}

	protected void onLoadEnd(FileReaderJSO reader, Object evt) {

		String dataUrl = reader.getResult();//

		String urlData = (dataUrl);//
		this.onDataLoad(urlData);
	}

	protected void onDataLoad(String urlData) {

		this.setData(urlData, true);//
	}
}
