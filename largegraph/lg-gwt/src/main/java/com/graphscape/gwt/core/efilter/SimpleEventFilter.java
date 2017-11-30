/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 1, 2012
 */
package com.graphscape.gwt.core.efilter;

import com.graphscape.gwt.core.commons.Path;
import com.graphscape.gwt.core.core.Event;
import com.graphscape.gwt.core.core.UiObjectI;
import com.graphscape.gwt.core.efilter.SimpleEventFilter;
import com.graphscape.gwt.core.reflect.InstanceOf;

/**
 * @author wu
 * 
 */
public class SimpleEventFilter implements Event.FilterI {

	private Path eventPath;

	private Class srcType;

	private Object source;

	protected SimpleEventFilter(Event.Type et) {
		this(et, (Class) null);
	}

	protected SimpleEventFilter(Event.Type et, UiObjectI obj) {
		this(et, null, obj);
	}

	protected SimpleEventFilter(Event.Type et, Class srcCls) {
		this(et, srcCls, null);
	}

	protected SimpleEventFilter(Event.Type et, Class srcCls, Object src) {
		this.eventPath = et.getAsPath();//
		this.srcType = srcCls;
		this.source = src;
	}

	public static SimpleEventFilter valueOf(Event.Type et, Object src) {
		return valueOf(et, null, src);
	}

	public static SimpleEventFilter valueOf(Event.Type et, Class srcCls) {
		return valueOf(et, srcCls, null);
	}

	public static SimpleEventFilter valueOf(Event.Type et, Class srcCls,
			Object src) {
		return new SimpleEventFilter(et, srcCls, src);
	}

	@Override
	public <T extends Event> T filter(Event e) {
		if (this.eventPath != null
				&& !this.eventPath.isSubPath(e.getPath(), true)) {
			return null;
		}

		if (this.srcType != null
				&& !InstanceOf.isInstance(this.srcType, e.getSource())) {
			return null;
		}
		if (this.source != null && !this.source.equals(e.getSource())) {
			return null;
		}
		return (T) e;

	}

	/*
	 * Nov 8, 2012
	 */
	@Override
	public String toString() {
		//
		return "class:" + this.getClass() + ",eventPath:" + this.eventPath
				+ ",source:" + this.source + ",srcType:" + this.srcType + "";
	}

}
