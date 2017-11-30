package com.graphscape.gwt.core.efilter;

import com.graphscape.gwt.core.core.Event;
import com.graphscape.gwt.core.core.UiObjectI;
import com.graphscape.gwt.core.efilter.SimpleEventFilter;
import com.graphscape.gwt.core.event.ClickEvent;

public class ClickEventFilter extends SimpleEventFilter implements
		Event.FilterI {

	public ClickEventFilter(UiObjectI src) {
		super(ClickEvent.TYPE, src);
	}

	@Override
	public <T extends Event> T filter(Event e) {
		if (null == super.filter(e)) {
			return null;
		}
		ClickEvent rt = (ClickEvent) e;

		return (T) rt;

	}

}
