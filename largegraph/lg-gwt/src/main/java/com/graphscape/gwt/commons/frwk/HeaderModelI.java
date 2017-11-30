/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 6, 2012
 */
package com.graphscape.gwt.commons.frwk;

import java.util.ArrayList;
import java.util.List;

import com.graphscape.gwt.commons.Position;
import com.graphscape.gwt.commons.event.HeaderItemDisplayNameUpdateEvent;
import com.graphscape.gwt.commons.event.HeaderItemEvent;
import com.graphscape.gwt.core.ModelI;
import com.graphscape.gwt.core.UiException;
import com.graphscape.gwt.core.commons.Path;
import com.graphscape.gwt.core.core.Event.EventHandlerI;
import com.graphscape.gwt.core.event.ModelValueEvent;
import com.graphscape.gwt.core.support.ModelSupport;

/**
 * @author wu
 * 
 */
public interface HeaderModelI extends ModelI {

	public static class ItemModel extends ModelSupport {

		protected String name;

		protected String displayName;

		public static final Location L_ISSELECTED = Location.valueOf("_isselected");

		public static final Location L_POSITION = Location.valueOf("_position");

		public static final Position P_LEFT = Position.valueOf("left");

		public static final Position P_RIGHT = Position.valueOf("right");

		public static final Position P_CENTER = Position.valueOf("center");

		public ItemModel(String name) {
			super(name);
			this.name = name;
		}

		public void addSelectHandler(EventHandlerI<ModelValueEvent> eh) {
			this.addValueHandler(L_ISSELECTED, eh);
		}

		public String getName() {
			return name;
		}

		public ItemModel getItem(String name, boolean force) {
			ItemModel rt = this.getChild(ItemModel.class, name, force);
			return rt;
		}

		public ItemModel addItem(String name) {
			ItemModel old = this.getChild(ItemModel.class, name, false);
			if (old != null) {
				throw new UiException("already exist name:" + name + " under item:" + this.getName());
			}
			ItemModel rt = new ItemModel(name);

			rt.parent(this);

			return rt;
		}

		public ItemModel addItem(String name, final ViewReferenceI mgd) {
			final ItemModel rt = this.addItem(name);
			this.addHandler(HeaderItemEvent.TYPE, new EventHandlerI<HeaderItemEvent>() {

				@Override
				public void handle(HeaderItemEvent e) {
					mgd.select(true);//
				}
			});

			return rt;
		}

		public void setPosition(Position p) {
			this.setValue(L_POSITION, p);
		}

		public Position getPosition() {
			return this.getValue(Position.class, L_POSITION, P_LEFT);
		}

		public int getItemDepth() {

			if (null == this.parent || !(this.parent instanceof ItemModel)) {
				return 0;
			}
			ItemModel p = (ItemModel) this.parent;
			return p.getItemDepth() + 1;
		}

		public Path getMenuPath() {
			if (null == this.parent || !(this.parent instanceof ItemModel)) {
				return Path.valueOf(this.name);

			}
			ItemModel p = (ItemModel) this.parent;
			Path pp = p.getMenuPath();
			List<String> nl = new ArrayList<String>(pp.getNameList());
			nl.add(this.name);
			return Path.valueOf(nl);

		}

		public boolean isSelected() {
			Boolean b = (Boolean) this.getValue(L_ISSELECTED);
			return b == null ? false : b;
		}

		/**
		 * Nov 25, 2012
		 */
		public void select(boolean b) {
			this.setValue(L_ISSELECTED, b);
		}

		/**
		 * @return the displayName
		 */
		public String getDisplayName() {
			return displayName;
		}

		/**
		 * @param displayName
		 *            the displayName to set
		 */
		public void setDisplayName(String displayName) {
			this.displayName = displayName;
			new HeaderItemDisplayNameUpdateEvent(this, this.displayName).dispatch();
		}

	}

	public static final String MK_ITEM_LIST = "ITEM_LIST";

	public ItemModel addItem(String name, Position pos);

	public ItemModel addItem(String name, Position pos, ViewReferenceI mgd);

	public ItemModel getItem(String name, boolean force);
	
	public ItemModel getItem(Path p,boolean force);

	public ItemModel getOrAdd(String name, Position pos);

	public ItemModel addItem(Path path, Position pos);

}
