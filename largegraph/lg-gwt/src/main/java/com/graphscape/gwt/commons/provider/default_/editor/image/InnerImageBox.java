/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 19, 2012
 */
package com.graphscape.gwt.commons.provider.default_.editor.image;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.graphscape.gwt.commons.drag.DragableI;
import com.graphscape.gwt.commons.drag.DraggerI;
import com.graphscape.gwt.commons.drag.event.DragEndEvent;
import com.graphscape.gwt.commons.drag.event.DragEvent;
import com.graphscape.gwt.commons.drag.event.DragStartEvent;
import com.graphscape.gwt.commons.drag.event.DraggingEvent;
import com.graphscape.gwt.commons.provider.default_.editor.image.InnerImageBox;
import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.ClientI;
import com.graphscape.gwt.core.commons.Size;
import com.graphscape.gwt.core.core.Event.EventHandlerI;
import com.graphscape.gwt.core.support.ElementObjectSupport;

/**
 * @author wu
 * 
 */
public class InnerImageBox extends ElementObjectSupport implements DragableI {
	EventHandlerI<DragEndEvent> eh;

	//
	protected Element imageDiv;
	protected Element log;
	/**
	 * @param ele
	 */
	public InnerImageBox(ContainerI c, Element div ,Element log, Size size) {
		super(c, DOM.createDiv());//box
		this.elementWrapper.setSize(size);//
		this.imageDiv = div;
		this.element.addClassName("inner-box");
		this.log = log;
		
	}

	@Override
	public void doAttach() {
		super.doAttach();
		ClientI c = this.getClient(true);//
		DraggerI drag = c.getChild(DraggerI.class, true);
		drag.addDragable(this);
		this.addHandler(DragStartEvent.TYPE, new EventHandlerI<DragStartEvent>() {

			@Override
			public void handle(DragStartEvent e) {
				InnerImageBox.this.onStart(e);
			}
		});
		this.addHandler(DragEndEvent.TYPE, new EventHandlerI<DragEndEvent>() {

			@Override
			public void handle(DragEndEvent e) {
				InnerImageBox.this.onEnd(e);
			}
		});
		this.addHandler(DraggingEvent.TYPE, new EventHandlerI<DraggingEvent>() {

			@Override
			public void handle(DraggingEvent e) {
				InnerImageBox.this.onDragging(e);
			}
		});

	}

	/**
	 * @param e
	 */
	protected void onDragging(DraggingEvent e) {

		log(e);
	}

	/**
	 * @param e
	 */
	protected void onEnd(DragEndEvent e) {
		log(e);
	}

	/**
	 * @param e
	 */
	protected void onStart(DragStartEvent e) {

		log(e);
	}

	protected void log(DragEvent e) {
		log(e.toString());
	}

	protected void log(String msg) {
		// this.element.setInnerText(msg);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.commons.uicommons.api.gwt.client.drag.DragableI#getOuterMostElement()
	 */
	@Override
	public Element getOuterMostElement() {
		return null;
	}

	/*
	 * Nov 24, 2012
	 */
	@Override
	public Element getDragingOnElement() {
		//
		return this.element;
	}

	/*
	 *Apr 21, 2013
	 */
	@Override
	public Element getMovingElement() {
		// 
		return this.imageDiv;
	}

	/*
	 *Apr 21, 2013
	 */
	@Override
	public Element getLogingElement() {
		// 
		return this.log;
	}
}
