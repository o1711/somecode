/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 6, 2012
 */
package com.graphscape.gwt.commons.provider.default_.frwk;

import com.graphscape.gwt.commons.event.HeaderItemEvent;
import com.graphscape.gwt.commons.frwk.BottomViewI;
import com.graphscape.gwt.commons.mvc.simple.SimpleView;
import com.graphscape.gwt.commons.provider.default_.frwk.BottomView;
import com.graphscape.gwt.commons.widget.bar.BarWidgetI;
import com.graphscape.gwt.commons.widget.basic.AnchorWI;
import com.graphscape.gwt.commons.widget.basic.LabelI;
import com.graphscape.gwt.commons.widget.list.ListI;
import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.commons.Path;
import com.graphscape.gwt.core.core.Event.EventHandlerI;
import com.graphscape.gwt.core.event.ClickEvent;

/**
 * @author wu
 * 
 */
public class BottomView extends SimpleView implements BottomViewI {

	private ListI outer;

	private BarWidgetI bar;

	private LabelI copyRight;

	/**
	 * @param ctn
	 */
	public BottomView(ContainerI c) {
		super(c, "bottom");
		this.outer = this.factory.create(ListI.class, this.getChildName("outer"), ListI.PK_IS_VERTICAL,
				Boolean.TRUE);
		this.outer.getElement().addClassName("bottom-list");
		this.outer.parent(this);
		{
			//
			this.bar = this.factory.create(BarWidgetI.class);
			this.bar.getElementWrapper().addClassName("bottom-bar");
			this.bar.parent(this.outer);//
		}
		{//
			this.copyRight = this.factory.create(LabelI.class);
			this.copyRight.getElementWrapper().addClassName("bottom-copyright");
			this.copyRight.parent(this.outer);
			this.copyRight.setText("/copyright", true);
		}
	}

	@Override
	public void addItem(final Path path) {
		//
		AnchorWI aw = this.factory.create(AnchorWI.class);
		// localized
		String txt = this.getClient(true).localized(path.toString());
		aw.setDisplayText(txt);
		aw.getElement().addClassName("bottom-item");

		this.bar.addItem(BarWidgetI.P_CENTER, aw);
		aw.addHandler(ClickEvent.TYPE, new EventHandlerI<ClickEvent>() {

			@Override
			public void handle(ClickEvent t) {
				//
				BottomView.this.onClick(path);
			}
		});

	}

	protected void onClick(Path path) {
		new HeaderItemEvent(this, HeaderItemEvent.TYPE.getAsPath().concat(path)).dispatch();

	}

}
