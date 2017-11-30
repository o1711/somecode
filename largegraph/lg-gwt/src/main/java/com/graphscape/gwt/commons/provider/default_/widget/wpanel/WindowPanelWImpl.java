/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 5, 2012
 */
package com.graphscape.gwt.commons.provider.default_.widget.wpanel;

import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.graphscape.gwt.commons.widget.support.LayoutSupport;
import com.graphscape.gwt.commons.widget.wpanel.WindowPanelWI;
import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.WindowI;
import com.graphscape.gwt.core.commons.Point;
import com.graphscape.gwt.core.commons.Size;
import com.graphscape.gwt.core.core.Event.EventHandlerI;
import com.graphscape.gwt.core.efilter.SimpleEventFilter;
import com.graphscape.gwt.core.event.SizeChangeEvent;

/**
 * @author wu
 *         <p>
 *         This can be the top widget and no parent,but it should be managed by
 *         RootI.
 * @see AbsolutePanel
 */
public class WindowPanelWImpl extends LayoutSupport implements WindowPanelWI {

	/**
	 * @param ele
	 */
	public WindowPanelWImpl(ContainerI c, String name) {
		super(c, name, DOM.createDiv());

		// DOM.setStyleAttribute(this.element, "position", "relative");//

		this.element.getStyle().setPosition(Position.ABSOLUTE);
		this.element.getStyle().setOverflow(Overflow.HIDDEN);
		this.getElementWrapper().moveTo(Point.valueOf(0, 0));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.commons.uicore.api.gwt.client.support.WidgetSupport#doAttach()
	 */
	@Override
	public void doAttach() {
		super.doAttach();
		WindowI w = this.getContainer().get(WindowI.class, true);
		//
		this.addHandler(SimpleEventFilter.valueOf(SizeChangeEvent.TYPE, WindowI.class, w),
				new EventHandlerI<SizeChangeEvent>() {

					@Override
					public void handle(SizeChangeEvent e) {
						WindowPanelWImpl.this.onWindowSizeChange(e);
					}
				});
	}

	protected void onWindowSizeChange(SizeChangeEvent e) {

		Size s = e.getSize();
		DOM.setStyleAttribute(this.element, "width", s.getWidth() + "px");
		DOM.setStyleAttribute(this.element, "height", s.getHeight() + "px");

	}
}
