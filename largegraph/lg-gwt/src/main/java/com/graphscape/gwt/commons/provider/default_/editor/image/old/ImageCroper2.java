/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 19, 2012
 */
package com.graphscape.gwt.commons.provider.default_.editor.image.old;

import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.DOM;
import com.graphscape.gwt.commons.drag.event.DragEndEvent;
import com.graphscape.gwt.commons.drag.event.DraggingEvent;
import com.graphscape.gwt.commons.provider.default_.editor.image.Canvas;
import com.graphscape.gwt.commons.provider.default_.editor.image.old.ImageBox2;
import com.graphscape.gwt.commons.provider.default_.editor.image.old.ImageCroper2;
import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.commons.Point;
import com.graphscape.gwt.core.commons.Rectangle;
import com.graphscape.gwt.core.commons.Size;
import com.graphscape.gwt.core.core.Event.EventHandlerI;
import com.graphscape.gwt.core.dom.ElementWrapper;
import com.graphscape.gwt.core.event.DataEvent;
import com.graphscape.gwt.core.gwthandlers.GwtClickHandler;
import com.graphscape.gwt.core.support.ElementObjectSupport;
import com.graphscape.gwt.core.support.ObjectElementHelper;

/**
 * @author wu
 * 
 */
public class ImageCroper2 extends ElementObjectSupport {

	protected ElementWrapper originImageWrapper;// the image,not change it

	protected ElementWrapper editingImageWrapper;

	protected ImageElement editingImage;

	protected Canvas canvas;// hidden this.

	protected ImageBox2 box;

	protected ElementWrapper test;// remove this.

	protected String originalData;

	protected String resultData;// image data url

	protected boolean debug;// show or hidden some element

	protected boolean boxImage;

	protected double zoomX = 1;

	protected double zoomY = 1;
	

	//protected ObjectElementHelper ok;

	/**
	 * @param ele
	 */
	public ImageCroper2(ContainerI c) {
		super(c, DOM.createDiv());
		this.element.addClassName("image-croper");
		// the origin image is used for determine the size of the image.
		// if set it to display:none,the size will be 0,0;
		// so we provide a opacity solution.
		this.originImageWrapper = ElementWrapper.valueOf(DOM.createImg());
		this.addOpacity(this.originImageWrapper);

		// canvas shoulb be hidden
		canvas = new Canvas(60, 60);
		canvas.parent(this);//

		//the image itself,
		editingImage = ImageElement.as(DOM.createImg());
		this.editingImageWrapper = ElementWrapper.valueOf(this.editingImage);//
		this.editingImageWrapper.addClassName("editingImage");

		this.elementWrapper.append(this.editingImageWrapper);

		this.box = new ImageBox2(c,this.editingImageWrapper.getElement());
		this.box.addHandler(DragEndEvent.TYPE, new EventHandlerI<DragEndEvent>() {

			@Override
			public void handle(DragEndEvent e) {
				ImageCroper2.this.onDragEnd(e);
			}
		});
		this.box.addHandler(DraggingEvent.TYPE, new EventHandlerI<DraggingEvent>() {

			@Override
			public void handle(DraggingEvent e) {
				ImageCroper2.this.onDragging(e);
			}
		});

		this.box.parent(this);

		this.test = ElementWrapper.valueOf(DOM.createImg());
		this.test.addClassName("test");
		this.elementWrapper.append(this.test);

		//
//		this.ok = this.helpers.addHelper("ok", DOM.createAnchor());
//		this.ok.parent(this.elementWrapper);
//		this.ok.getElement().setInnerHTML("ok");
//		this.ok.addGwtHandler(ClickEvent.getType(), new GwtClickHandler() {
//
//			@Override
//			protected void handleInternal(ClickEvent evt) {
//				ImageCroper.this.close(true);
//			}
//		});
		this.forDebug();

	}

	private void forDebug() {
		this.canvas.getElementWrapper().setVisible(this.debug);

		this.test.setVisible(this.debug);

	}

	protected void onDragging(DraggingEvent e) {
		// this.buildResultDataUrl(false);
	}

	/**
	 * Nov 19, 2012
	 */
	protected void onDragEnd(DragEndEvent e) {
		this.buildResultDataUrl(true);
	}

	public void setDataUrl(String data) {
		this.originalData = data;
		this.originImageWrapper.setAttribute("src", data);//
		this.editingImageWrapper.setAttribute("src", data);// TODO empty
		// zoom
		this.box.getElementWrapper().tryMoveInside(Point.valueOf(0, 0),
				this.editingImageWrapper);// TODO center of the outer.
		this.buildResultDataUrl(true);
	}

	// zoom the editing image?

	public void zoom(double zoom) {
		this.zoomX = zoom;
		this.zoomY = zoom;
		Size size = this.getOrininalSize();

		this.editingImageWrapper.resize(size.multiple(zoom));

	}

	public Size getOrininalSize() {
		return this.originImageWrapper.getAbsoluteRectangle().getSize();
	}

	public String getOriginalDataUrl() {
		return this.originalData;
	}

	protected void buildResultDataUrl(boolean dispatch) {
		this.resultData = this.doBuildResultDataUrl();
		if (dispatch) {
			new DataEvent(this, this.resultData).dispatch();// data changing
															// event.
		}
		// this.box.setImageDataUrl(this.resultData);// update the box.
		// TODO
		this.test.setAttribute("src", this.resultData);//

	}

	protected String doBuildResultDataUrl() {
		this.canvas.clear();//

		Rectangle selectedRect = this.box.getElementWrapper()
				.getAbsoluteRectangle();// selected area

		Rectangle editingRect = this.editingImageWrapper.getAbsoluteRectangle();// image
																				// area
		// Size editingSize = editingRect.getSize();
		Size imageSize = this.originImageWrapper.getAbsoluteRectangle()
				.getSize();//
		//
		/*
		 * double zoomX = (double) imageSize.getWidth() / (double)
		 * editingRect.getSize().getWidth(); double zoomY = (double)
		 * imageSize.getHeight() / (double) editingRect.getSize().getHeight();
		 */
		// calculate the actrual size

		Point offsetTopLeft = selectedRect.getTopLeft().minus(
				editingRect.getTopLeft());

		double sx = Math.max(0, offsetTopLeft.getX()) * zoomX;
		double sy = Math.max(0, offsetTopLeft.getY()) * zoomY;

		double sw = selectedRect.getSize().getWidth() * zoomX;
		double sh = selectedRect.getSize().getHeight() * zoomY;

		Size canvasSize = this.canvas.getCanvasSize();

		double tw = canvasSize.getWidth();
		double th = canvasSize.getHeight();
		// zooming
		// if the selected box is large than the image

		sw = (sx + sw) > imageSize.getWidth() ? imageSize.getWidth() : sw;
		sh = (sy + sh) > imageSize.getHeight() ? imageSize.getHeight() : sh;

		if (this.originalData != null) {
			this.canvas.drawImage(this.editingImage, sx, sy, sw, sh, 0, 0, tw,
					th);
		}
		//
		// TODO return a null or ?
		return this.canvas.toDataUrl();

	}

	/**
	 * Nov 21, 2012
	 */
	public void open() {
		this.elementWrapper.setVisible(true);//
	}

	public void close(boolean ok) {
		this.elementWrapper.setVisible(false);
		if (ok) {
			new DataEvent(this, this.resultData).dispatch();
		}
		this.getElement().removeFromParent();//
	}

}
