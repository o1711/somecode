/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 19, 2012
 */
package com.fs.uiclient.impl.gwt.client.expe;

import java.util.HashMap;
import java.util.Map;

import com.fs.uiclient.api.gwt.client.Actions;
import com.fs.uiclient.api.gwt.client.UiClientConstants;
import com.fs.uiclient.api.gwt.client.exps.ExpEditViewI;
import com.fs.uicommons.api.gwt.client.editor.basic.StringEditorI;
import com.fs.uicommons.api.gwt.client.editor.image.ImageCropEditorI;
import com.fs.uicommons.api.gwt.client.frwk.commons.FieldModel;
import com.fs.uicommons.api.gwt.client.frwk.commons.FormViewI;
import com.fs.uicommons.api.gwt.client.widget.EditorI;
import com.fs.uicommons.api.gwt.client.widget.event.ChangeEvent;
import com.fs.uicommons.impl.gwt.client.frwk.commons.form.FormsView;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.commons.Size;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;

/**
 * @author wu
 * 
 */
public class ExpEditView extends FormsView implements ExpEditViewI {

	/**
	 * @param ctn
	 */
	public ExpEditView(ContainerI ctn) {
		super(ctn, "expe");
		this.addAction(Actions.A_EXPE_SUBMIT);//

		FormViewI fv = this.getDefaultForm();
		{
			fv.addField(F_TITLE, String.class);
		}
		// fv.addField(F_SUMMARY, String.class, pts);
		{
			Map<String, Object> pts = new HashMap<String, Object>();
			pts.put(StringEditorI.PK_TEXAREA, true);

			FieldModel fm = fv.addField(F_BODY, String.class, pts);
			EditorI<String> e = fm.getEditor(true);
			e.addHandler(ChangeEvent.TYPE, new EventHandlerI<ChangeEvent>() {

				@Override
				public void handle(ChangeEvent t) {
					ExpEditView.this.onBodyChange(t);
				}
			});
		}
		//
//		{
//			Map<String, Object> pts = new HashMap<String, Object>();
//			
//			FieldModel iconFM = fv.addField(ExpEditViewI.F_ICON, String.class, ImageCropEditorI.class, pts);
//		}
		{
			Map<String, Object> pts = new HashMap<String, Object>();
			pts.put(ImageCropEditorI.PK_TARGET_SIZE,UiClientConstants.EXPEDIT_IMAGE_SIZE);
			pts.put(ImageCropEditorI.PK_INNER_BOX_ZOOM, UiClientConstants.EXPEDIT_INNER_BOX_ZOOM);
			
			pts.put(ImageCropEditorI.PK_OUTER_BOX_ZOOMX, 1.5d);
			pts.put(ImageCropEditorI.PK_OUTER_BOX_ZOOMY, 1.5d);
			
			FieldModel iconFM = fv.addField(ExpEditViewI.F_IMAGE, String.class, ImageCropEditorI.class, pts);
		}

		fv.getFormModel().addAction(Actions.A_EXPE_SUBMIT);//
	}

	/**
	 * Apr 13, 2013
	 */
	protected void onBodyChange(ChangeEvent t) {
		String body = t.getData();
		body = body == null ? "" : body;
		FormViewI fv = this.getDefaultForm();
		String title = fv.getFieldData(F_TITLE);
		if (title == null) {
			if (body.length() < 56) {
				title = body;
			} else {
				title = body.substring(0, 56 - 3) + "...";
			}
			fv.setFieldValue(F_TITLE, title);
		}

	}

}
