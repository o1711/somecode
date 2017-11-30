/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 19, 2012
 */
package com.graphscape.gwt.commons.provider.default_.editor.image;

import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.graphscape.gwt.commons.drag.event.DragEndEvent;
import com.graphscape.gwt.commons.drag.event.DragStartEvent;
import com.graphscape.gwt.commons.drag.event.DraggingEvent;
import com.graphscape.gwt.commons.provider.default_.dom.TDWrapper;
import com.graphscape.gwt.commons.provider.default_.dom.TRWrapper;
import com.graphscape.gwt.commons.provider.default_.dom.TableWrapper;
import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.HandlerI;
import com.graphscape.gwt.core.commons.Point;
import com.graphscape.gwt.core.commons.Rectangle;
import com.graphscape.gwt.core.commons.Size;
import com.graphscape.gwt.core.core.Event.EventHandlerI;
import com.graphscape.gwt.core.dom.ElementWrapper;
import com.graphscape.gwt.core.event.DataEvent;
import com.graphscape.gwt.core.gwthandlers.GwtClickHandler;
import com.graphscape.gwt.core.scheduler.SchedulerI;
import com.graphscape.gwt.core.support.ElementObjectSupport;
import com.graphscape.gwt.core.support.ObjectElementHelper;

/**
 * @author wu
 * 
 */
public class ImageCroper extends ElementObjectSupport {

	protected ElementWrapper originImageWrapper;// the image,not change it

	protected ElementWrapper editingImageWrapper;

	protected ElementWrapper editingImageBox;

	protected ImageElement editingImage;

	protected Canvas canvas;// hidden this.

	protected InnerImageBox innerBox;

	protected ElementWrapper test;// remove this.

	protected Element outerBox;

	protected String originalData;

	protected String resultData;// image data url

	protected boolean debug;// show or hidden some element

	protected boolean boxImage;

	protected double zoomX = 1;

	protected double zoomY = 1;

	protected TableWrapper table;

	protected Element labelOfMouseMoving;

	// plus the size
	protected ObjectElementHelper plus;

	// minus the size
	protected ObjectElementHelper minus;

	protected Size targetSize;

	protected Size innerBoxSize;

	protected Size outerBoxSize;

	/**
	 * @param ele
	 */
	public ImageCroper(ContainerI c, Size targetSize, double innerBoxZoom, double outerBoxZoomX,
			double outerBoxZoomY) {
		super(c, DOM.createDiv());
		this.targetSize = targetSize;
		this.innerBoxSize = this.targetSize.multiple(innerBoxZoom);
		this.outerBoxSize = this.innerBoxSize.multiple(outerBoxZoomX, outerBoxZoomY);
		this.element.addClassName("image-croper");

		{
			// the origin image is used for determine the size of the image.
			// if set it to display:none,the size will be 0,0;
			// so we provide a opacity solution.
			this.originImageWrapper = ElementWrapper.valueOf(DOM.createImg());
			this.addOpacity(this.originImageWrapper);
		}
		{
			// canvas shoulb be hidden

			canvas = new Canvas(this.targetSize);
			canvas.parent(this);//
		}
		{
			this.outerBox = DOM.createDiv();
			this.outerBox.addClassName("outer-box");
			this.element.appendChild(this.outerBox);//
		}
		{// image
			// the image itself,
			editingImage = ImageElement.as(DOM.createImg());

			this.editingImageWrapper = ElementWrapper.valueOf(this.editingImage);//

			editingImageBox = new ElementWrapper(DOM.createDiv());

			editingImageBox.append(editingImageWrapper);
			editingImageBox.addClassName("editing-image-box");
			this.outerBox.appendChild(editingImageBox.getElement());

		}
		{
			this.table = new TableWrapper();
			// NOTE,size of outer box.
			this.table.resize(this.outerBoxSize);// Size of outer box

			this.table.addClassName("outer-box");//
			this.outerBox.appendChild(this.table.getElement());//

			int width00 = (this.outerBoxSize.getWidth() - this.innerBoxSize.getWidth()) / 2;
			int width01 = this.innerBoxSize.getWidth();
			int width02 = width00;

			int height01 = (this.outerBoxSize.getHeight() - this.innerBoxSize.getHeight()) / 2;
			int height11 = this.innerBoxSize.getHeight();
			int height21 = height01;
			{// tr1
				TRWrapper tr = this.table.addTr();
				TDWrapper td0 = tr.addTd();
				td0.addClassName("td00");
				//
				td0.setWidth(width00);
				Element div = DOM.createDiv();
				div.addClassName("image-croper-toolbar");
				td0.append(div);
				td0.setRowSpan(3);
				{

					this.plus = this.helpers.addHelper("plus", DOM.createButton());
					this.plus.getElement().setInnerText("+");
					this.plus.addClassName("plus");
					this.plus.addClassName("zoomer");
					div.appendChild(this.plus.getElement());

					this.plus.addGwtHandler(ClickEvent.getType(), new GwtClickHandler() {

						@Override
						protected void handleInternal(ClickEvent evt) {
							ImageCroper.this.onPlus();
						}
					});
					this.minus = this.helpers.addHelper("minus", DOM.createButton());
					this.minus.getElement().setInnerText("-");
					this.minus.addClassName("minus");
					this.minus.addClassName("zoomer");
					div.appendChild(this.minus.getElement());
					this.minus.addGwtHandler(ClickEvent.getType(), new GwtClickHandler() {

						@Override
						protected void handleInternal(ClickEvent evt) {
							ImageCroper.this.onMinus();
						}
					});
				}
				TDWrapper td1 = tr.addTd();
				{
					this.labelOfMouseMoving = td1.getElement();
					this.labelOfMouseMoving.addClassName("mouse-position");
				}
				td1.addClassName("td01");
				td1.setWidth(width01);
				td1.setHeight(height01);
				TDWrapper td2 = tr.addTd();
				td2.setRowSpan(3);
				td2.addClassName("td02");
				td2.setWidth(width02);
			}
			{// tr2
				TRWrapper tr = this.table.addTr();

				TDWrapper td1 = tr.addTd();
				td1.addClassName("td11");
				td1.setHeight(height11);
				{// box is here,center of the table

					this.innerBox = new InnerImageBox(c, this.editingImageBox.getElement(),
							this.labelOfMouseMoving, this.innerBoxSize);// size
																		// is
																		// here

					this.innerBox.addHandler(DragEndEvent.TYPE, new EventHandlerI<DragEndEvent>() {

						@Override
						public void handle(DragEndEvent e) {
							ImageCroper.this.onDragEnd(e);
						}
					});
					this.innerBox.addHandler(DragStartEvent.TYPE, new EventHandlerI<DragStartEvent>() {

						@Override
						public void handle(DragStartEvent e) {
							ImageCroper.this.onDragStart(e);
						}
					});
					this.innerBox.addHandler(DraggingEvent.TYPE, new EventHandlerI<DraggingEvent>() {

						@Override
						public void handle(DraggingEvent e) {
							ImageCroper.this.onDragging(e);
						}
					});

					// note,already ad to the center td.
					td1.append(this.innerBox.getElement());
					this.innerBox.parent(this);

				}

			}
			{// tr 3
				TRWrapper tr = this.table.addTr();

				TDWrapper td1 = tr.addTd();
				td1.addClassName("td21");
				td1.setHeight(height21);
			}

		}

		this.test = ElementWrapper.valueOf(DOM.createImg());
		this.test.addClassName("test");
		this.elementWrapper.append(this.test);

		//
		// this.ok = this.helpers.addHelper("ok", DOM.createAnchor());
		// this.ok.parent(this.elementWrapper);
		// this.ok.getElement().setInnerHTML("ok");
		// this.ok.addGwtHandler(ClickEvent.getType(), new GwtClickHandler() {
		//
		// @Override
		// protected void handleInternal(ClickEvent evt) {
		// ImageCroper.this.close(true);
		// }
		// });
		this.forDebug();

	}

	private void forDebug() {
		this.canvas.getElementWrapper().setVisible(this.debug);

		this.test.setVisible(this.debug);

	}

	protected void onPlus() {
		this.zoom(this.zoomX * 1.1, this.zoomY * 1.1, true);
	}

	protected void onMinus() {
		this.zoom(this.zoomX * 0.9, this.zoomY * 0.9, true);

	}

	protected void onDragging(DraggingEvent e) {
		// this.buildResultDataUrl(false);
		// this.showEventPoint(e);
	}

	/**
	 * Nov 19, 2012
	 */
	protected void onDragEnd(DragEndEvent e) {
		this.elementWrapper.removeClassName("moving");
		this.buildResultDataUrl(true);
	}

	protected void onDragStart(DragStartEvent e) {
		this.elementWrapper.addClassName("moving");
	}

	public void setDataUrl(String data) {
		this.originalData = data;
		if (this.originalData == null) {
			// TODO remove image.
			return;
		}
		this.originImageWrapper.setAttribute("src", data);//
		this.editingImageWrapper.setAttribute("src", data);// TODO empty
		// zoom to fit the size of this box
		// currently the size of image is 0,0,so try to do delay.
		SchedulerI s = this.container.get(SchedulerI.class, true);
		s.scheduleTimer(200, new HandlerI<Object>() {

			@Override
			public void handle(Object t) {
				//
				ImageCroper.this.afterSetDataUrl();

			}
		});
	}

	private void afterSetDataUrl() {
		Size osize = this.getOrininalSize();
		if (osize.getHeight() == 0 || osize.getWidth() == 0) {
			// NOTE,
			return;
		}

		// initial, zoom to proper size,fit the outer box.

		Size tsize = this.outerBoxSize;

		int w1 = osize.getWidth();
		int w2 = tsize.getWidth();
		double zx = w1 == 0 ? 1 : (double) w2 / (double) w1;

		int h1 = osize.getHeight();
		int h2 = tsize.getHeight();
		double zy = h1 == 0 ? 1 : (double) h2 / (double) h1;

		double zoom = zx > zy ? zx : zy;// Zoom to large enough.
		this.zoom(zoom, zoom, false);
	}

	// zoom the editing image?

	public boolean zoom(double zx, double zy, boolean keepcenter) {
		Size size = this.getOrininalSize();
		Size tsize = size.multiple(zx, zy);
		if (tsize.getHeight() < 100 || tsize.getWidth() < 100) {
			// too small
			return false;
		}
		double oldZx = this.zoomX;
		double oldZy = this.zoomY;
		this.zoomX = zx;
		this.zoomY = zy;

		if (keepcenter) {
			// vector2-vector1=vector3

			Point absCenter1 = this.innerBox.getElementWrapper().getAbsoluteRectangle().getCenter();

			Point absOuterTopleft = new ElementWrapper(this.outerBox).getAbsoluteRectangle().getTopLeft();

			// NOte,the center of the innerbox in the system of the outer box.
			Point vecter1 = absCenter1.minus(absOuterTopleft);

			Point absImageBoxTopLeft = this.editingImageBox.getAbsoluteRectangle().getTopLeft();// current

			// Note, the current top left of the image box in the system of the
			// outer box
			// This is also the offset topleft of the image box.
			Point vecter2 = absImageBoxTopLeft.minus(absOuterTopleft);//

			// Note, the top left of the image box te be zoom/scale
			Point vecter3 = vecter2.minus(vecter1);
			//
			// NOTE,zoom scale from current size
			double zoomX2 = this.zoomX / oldZx;
			double zoomY2 = this.zoomY / oldZy;
			int x32 = (int) (vecter3.getX() * zoomX2);
			int y32 = (int) (vecter3.getY() * zoomY2);
			// the target vector3
			Point vecter32 = Point.valueOf(x32, y32);
			// the tareget
			Point vector22 = vecter1.add(vecter32.getX(), vecter32.getY());

			// keep center of the image box
			// Note, move the box that contains the image,not the image itself

			this.editingImageBox.moveTo(vector22);

		}
		this.editingImageWrapper.resize(tsize);
		this.buildResultDataUrl(true);
		return true;
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

		Rectangle selectedRect = this.innerBox.getElementWrapper().getAbsoluteRectangle();// selected
																							// area

		Rectangle editingRect = this.editingImageWrapper.getAbsoluteRectangle();// image
																				// area
		// Size editingSize = editingRect.getSize();
		Size imageSize = this.originImageWrapper.getAbsoluteRectangle().getSize();//
		//
		/*
		 * double zoomX = (double) imageSize.getWidth() / (double)
		 * editingRect.getSize().getWidth(); double zoomY = (double)
		 * imageSize.getHeight() / (double) editingRect.getSize().getHeight();
		 */
		// calculate the actrual size

		Point offsetTopLeft = selectedRect.getTopLeft().minus(editingRect.getTopLeft());

		double sx = Math.max(0, offsetTopLeft.getX()) / zoomX;
		double sy = Math.max(0, offsetTopLeft.getY()) / zoomY;

		double sw = selectedRect.getSize().getWidth() / zoomX;
		double sh = selectedRect.getSize().getHeight() / zoomY;

		Size canvasSize = this.canvas.getCanvasSize();

		double tw = canvasSize.getWidth();
		double th = canvasSize.getHeight();
		// zooming
		// if the selected box is large than the image

		sw = (sx + sw) > imageSize.getWidth() ? imageSize.getWidth() : sw;
		sh = (sy + sh) > imageSize.getHeight() ? imageSize.getHeight() : sh;

		if (this.originalData != null) {
			this.canvas.drawImage(this.editingImage, sx, sy, sw, sh, 0, 0, tw, th);
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
