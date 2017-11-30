package com.graphscape.gwt.core.efilter;

import com.graphscape.gwt.core.core.Event;
import com.graphscape.gwt.core.efilter.SimpleEventFilter;
import com.graphscape.gwt.core.event.ModelEvent;

public class ModelEventFilter extends SimpleEventFilter implements
		Event.FilterI {

	private String replay;

	public ModelEventFilter(Event.Type type) {
		this(type, null);
	}

	public ModelEventFilter(Event.Type type, String replay) {
		super(type);
		this.replay = replay;
	}

	@Override
	public <T extends Event> T filter(Event e) {
		if (null == super.filter(e)) {
			return null;
		}
		ModelEvent mve = (ModelEvent) e;

		if (mve.isReplay() && !mve.isReplay(this.replay)) {// is other replay
			return null;
		}

		return (T) mve;

	}

}
