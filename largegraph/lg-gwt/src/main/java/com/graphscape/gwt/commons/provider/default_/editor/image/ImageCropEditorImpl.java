/**
 * All right is from Author of the file.
 * Any usage of the code must be authorized by the the auther.
 * If not sure the detail of the license,please distroy the copies immediately.  
 * Nov 19, 2012
 */
package com.graphscape.gwt.commons.provider.default_.editor.image;

import com.google.gwt.user.client.DOM;
import com.graphscape.gwt.commons.editor.image.ImageCropEditorI;
import com.graphscape.gwt.commons.editor.support.FileUrlDataEditorSupport;
import com.graphscape.gwt.commons.provider.default_.editor.image.ImageCropEditorImpl;
import com.graphscape.gwt.commons.provider.default_.editor.image.ImageCroper;
import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.commons.Size;
import com.graphscape.gwt.core.commons.UiPropertiesI;
import com.graphscape.gwt.core.core.Event.EventHandlerI;
import com.graphscape.gwt.core.dom.ElementWrapper;
import com.graphscape.gwt.core.event.DataEvent;

/**
 * @author wuzhen
 *         <p>
 *         http://code.google.com/p/gwt-examples/wiki/gwt_hmtl5#Crop_Image
 */
public class ImageCropEditorImpl extends FileUrlDataEditorSupport implements ImageCropEditorI {

	protected ElementWrapper image;

	protected ImageCroper imageCroper;

	private static final Size DEFAULT_SIZE = Size.valueOf(52, 52);//

	protected Size targetSize;
	protected double innerBoxZoom;
	protected double outerBoxZoomX;
	protected double outerBoxZoomY;

	public ImageCropEditorImpl(ContainerI c, String name, UiPropertiesI<Object> pts) {
		super(c, name, pts);

		this.targetSize = (Size) this.getProperty(ImageCropEditorI.PK_TARGET_SIZE, DEFAULT_SIZE);

		this.innerBoxZoom = (Double) this.getProperty(ImageCropEditorI.PK_INNER_BOX_ZOOM, 1.0d);
		this.outerBoxZoomX = (Double) this.getProperty(ImageCropEditorI.PK_OUTER_BOX_ZOOMX, 3.0d);
		this.outerBoxZoomY = (Double) this.getProperty(ImageCropEditorI.PK_OUTER_BOX_ZOOMY, 2.0d);

		this.image = ElementWrapper.valueOf(DOM.createImg());
		if (this.functional) {
			this.dataRender.append(this.image);//
		}

	}

	/*
	 * Nov 21, 2012
	 */
	@Override
	public void setData(String data) {
		super.setData(data);
		String src = data;
		if (data == null) {
			src = "";
		}

		this.image.setAttribute("src", src);
	}

	// data is from file input,not setData directly,process it first,see
	// onImageCroperData() method.
	@Override
	protected void onDataLoad(String data) {

		if (this.imageCroper != null) {
			this.imageCroper.getElement().removeFromParent();//
			this.imageCroper = null;
		}

		imageCroper = new ImageCroper(this.container, this.targetSize, this.innerBoxZoom, this.outerBoxZoomX,
				this.outerBoxZoomY);

		imageCroper.parent(this);//
		imageCroper.addHandler(DataEvent.TYPE, new EventHandlerI<DataEvent>() {

			@Override
			public void handle(DataEvent e) {
				ImageCropEditorImpl.this.onImageCroperData(e);
			}
		});

		imageCroper.setDataUrl(data);
		// open croper
		imageCroper.open();

	}

	/**
	 * Nov 21, 2012
	 */
	protected void onImageCroperData(DataEvent e) {
		//
		String ds = e.getData();

		this.setData((ds), true);

	}

}
