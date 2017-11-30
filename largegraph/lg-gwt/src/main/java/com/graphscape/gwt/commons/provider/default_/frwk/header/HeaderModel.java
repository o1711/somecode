/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 6, 2012
 */
package com.graphscape.gwt.commons.provider.default_.frwk.header;

import java.util.List;

import com.graphscape.gwt.commons.Position;
import com.graphscape.gwt.commons.event.HeaderItemEvent;
import com.graphscape.gwt.commons.frwk.HeaderModelI;
import com.graphscape.gwt.commons.frwk.ViewReferenceI;
import com.graphscape.gwt.commons.provider.default_.frwk.header.HeaderModel;
import com.graphscape.gwt.core.UiException;
import com.graphscape.gwt.core.commons.Path;
import com.graphscape.gwt.core.core.Event.EventHandlerI;
import com.graphscape.gwt.core.event.ModelValueEvent;
import com.graphscape.gwt.core.support.ModelSupport;

/**
 * @author wu
 * 
 */
public class HeaderModel extends ModelSupport implements HeaderModelI {

	/**
	 * @param c
	 */
	public HeaderModel(String name) {
		super(name);

	}

	@Override
	public ItemModel addItem(String name, Position pos) {
		return this.addItem(name, pos, null);
	}

	@Override
	public ItemModel addItem(String name, Position pos, final ViewReferenceI mgd) {
		final ItemModel rt = new ItemModel(name);
		rt.setPosition(pos);
		rt.addHandler(HeaderItemEvent.TYPE, new EventHandlerI<HeaderItemEvent>() {

			@Override
			public void handle(HeaderItemEvent e) {
				HeaderModel.this.onItemTrigger(mgd, rt);
			}
		});
		rt.parent(this).cast();

		// exclusive trigger
		rt.addSelectHandler(new EventHandlerI<ModelValueEvent>() {

			@Override
			public void handle(ModelValueEvent e) {
				HeaderModel.this.onItemSelect(rt);
			}
		});

		return rt;
	}

	/**
	 * Nov 26, 2012
	 */
	protected void onItemTrigger(ViewReferenceI mgd, ItemModel rt) {
		//
		rt.select(!rt.isSelected());//
		if (mgd != null) {
			mgd.select(true);//
		}
		// change the select state

	}

	/**
	 * Nov 25, 2012
	 */
	protected void onItemSelect(ItemModel rt) {
		// disable other item
		boolean sel = rt.isSelected();
		if (!sel) {
			return;// do nothing when unselect
		}

		// selected,only one is selected,other unselect
		for (ItemModel it : this.getChildList(ItemModel.class)) {
			if (rt == it) {
				continue;
			}
			//
			it.select(false);
		}
	}

	@Override
	public ItemModel getItem(String name, boolean force) {
		return this.getChild(ItemModel.class, name, force);
	}

	@Override
	public ItemModel getItem(Path p, boolean force) {

		ItemModel rt = this.getItem(null, p);
		if (rt == null && force) {
			throw new UiException("not found item in header with path:" + p);
		}

		return rt;
	}

	protected ItemModel getItem(ItemModel prt, Path path) {

		List<String> names = path.getNameList();

		if (names.isEmpty()) {
			return prt;
		}
		String name = names.get(0);
		ItemModel rt = null;
		if (prt == null) {
			rt = this.getItem(name, false);
		} else {
			rt = prt.getItem(name, false);
		}
		if (rt == null) {

			return null;
		}
		names = names.subList(1, names.size());
		return getItem(rt, Path.valueOf(names));
	}

	/*
	 * Nov 22, 2012
	 */
	@Override
	public ItemModel getOrAdd(String name, Position pos) {
		//
		ItemModel rt = this.getItem(name, false);
		if (rt == null) {
			rt = this.addItem(name, pos);
		}
		return rt;
	}

	/*
	 * Nov 22, 2012
	 */
	@Override
	public ItemModel addItem(Path path, Position pos) {
		//
		ItemModel rt = null;
		if (path.size() == 1) {
			rt = this.addItem(path.getName(), pos);
		} else if (path.size() == 2) {
			ItemModel parent = this.getOrAdd(path.getNameList().get(0), pos);
			rt = parent.addItem(path.getNameList().get(1));

		} else {
			throw new UiException("not supported:" + path);
		}
		return rt;
	}
}
