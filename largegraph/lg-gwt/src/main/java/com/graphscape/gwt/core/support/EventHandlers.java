/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 3, 2012
 */
package com.graphscape.gwt.core.support;

import java.util.ArrayList;
import java.util.List;

import com.graphscape.gwt.core.core.Event;
import com.graphscape.gwt.core.core.UiObjectI;
import com.graphscape.gwt.core.core.Event.EventHandlerI;
import com.graphscape.gwt.core.core.Event.FilterI;

/**
 * @author wu
 *         <p>
 *         each UiObjectI has one this instance as helper. for derfered attach
 *         to the event bus.
 */
public class EventHandlers {
	public static class Entry {
		private FilterI filter;
		private EventHandlerI handler;
		private boolean attached;

		Entry(FilterI f, EventHandlerI h, boolean attached) {
			this.filter = f;
			this.handler = h;
			this.attached = attached;
		}
	}

	protected UiObjectI owner;

	protected List<Entry> entryList = new ArrayList<Entry>();

	public EventHandlers(UiObjectI owner) {
		this.owner = owner;
	}

	public <E extends Event> void addHandler(FilterI ef, EventHandlerI<E> eh) {
		boolean attached = this.owner.isAttached();
		Entry e = new Entry(ef, eh, attached);
		this.entryList.add(e);
		if (attached) {
			this.attach(e);
		}
	}

	protected void attach(Entry e) {
		this.owner.getEventBus(true).addHandler(e.filter, e.handler);
		e.attached = true;
	}

	/**
	 * 
	 */
	public void onOwnerAttach() {
		for (Entry e : this.entryList) {
			if (e.attached) {
				continue;
			}
			this.attach(e);
		}
	}

	public void onOwnerDettach() {
		// TODO
	}
}
